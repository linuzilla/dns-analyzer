package ncu.cc.digger.services;

import ncu.cc.commons.utils.StringUtil;
import ncu.cc.digger.entities.ReportEntity;
import ncu.cc.digger.repositories.ReportRepository;
import ncu.cc.digger.utils.DNSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class ZoneQueryWithCacheServiceImpl implements ZoneQueryWithCacheService {
    private static final Logger logger = LoggerFactory.getLogger(ZoneQueryWithCacheServiceImpl.class);
    private final ReportRepository reportRepository;
    private final DigWithCacheService digWithCacheService;
    private final ZoneQueryService zoneQueryService;

    public ZoneQueryWithCacheServiceImpl(ReportRepository reportRepository, DigWithCacheService digWithCacheService, ZoneQueryService zoneQueryService) {
        this.reportRepository = reportRepository;
        this.digWithCacheService = digWithCacheService;
        this.zoneQueryService = zoneQueryService;
    }

    private class ZoneInDatabaseImpl implements ZoneInDatabase, ZoneBeforeAnalysis {
        private final String zone;
        private Optional<ReportEntity> reportEntity;
        private String belongingZone;
        private boolean shouldContinue;

        private ZoneInDatabaseImpl(String zone, Optional<ReportEntity> reportEntity) {
            this.zone = zone;
            this.reportEntity = reportEntity;
            this.shouldContinue = false;
        }

        @Override
        public ZoneInDatabase ifAlreadyInDatabase(Consumer<ReportEntity> consumer) {
            if (shouldContinue) {
                reportEntity.ifPresent(consumer::accept);
            }
            return this;
        }

        @Override
        public ZoneBeforeAnalysis ifNotInDatabase(Consumer<ZoneBeforeAnalysis> consumer) {
            if (shouldContinue && this.belongingZone != null && reportEntity.isEmpty()) {
                consumer.accept(this);
            }
            return this;
        }

        @Override
        public String belongingZoneName() {
            return this.belongingZone;
        }

        @Override
        public void triggerAnalyzer(String remoteAddress, boolean reload) {
            zoneQueryService.triggerQueryZone(this.belongingZone,remoteAddress, reload);
        }

        @Override
        public <R> R map(Function<ZoneBeforeAnalysis, ? extends R> mapper) {
            return mapper.apply(this);
        }
    }

    private ZoneInDatabaseImpl findInDb(String zone) {
        return new ZoneInDatabaseImpl(zone, reportRepository.findById(zone));
    }

    private void findZoneBelonging(ZoneInDatabaseImpl zoneInDatabase) {
        var normalizedBelongingZone = digWithCacheService.findBelongingZone(zoneInDatabase.zone).block();

        if (StringUtil.isNotEmpty(normalizedBelongingZone)) {
            var foundDomain = DNSUtil.stripTrailingDot(normalizedBelongingZone);

            if (foundDomain.equalsIgnoreCase(zoneInDatabase.zone)) {
                zoneInDatabase.belongingZone = foundDomain;
                logger.info("domain {} found", foundDomain);
            } else {
                zoneInDatabase.belongingZone = foundDomain;
                logger.info("domain {} belongs to {}", zoneInDatabase.zone, foundDomain);
            }
        }
    }

    @Override
    public Optional<ZoneInDatabase> findZone(String input, BiFunction<String,ZoneRetrieveState, Boolean> biFunction) {
        Optional<String> zone = DNSUtil.verifyOrRetrieveZone(input);

        if (zone.isPresent()) {
            ZoneInDatabaseImpl inDb = findInDb(zone.get());
            if (inDb.reportEntity.isEmpty()) {
                findZoneBelonging(inDb);

                if (inDb.belongingZone != null && ! inDb.zone.equals(inDb.belongingZone)) {
                    logger.info("discover zone: {}", inDb.belongingZone);
                    inDb.shouldContinue = biFunction.apply(inDb.belongingZone, ZoneRetrieveState.BELONGING);
                    inDb.reportEntity = reportRepository.findById(inDb.belongingZone);
                } else {
                    logger.info("true zone: {}", inDb.belongingZone);
                    inDb.shouldContinue = biFunction.apply(inDb.belongingZone, ZoneRetrieveState.EXACT);
                }
            } else {
                logger.info("in database: {}", zone);
                inDb.shouldContinue = biFunction.apply(inDb.belongingZone, ZoneRetrieveState.FOUND);
            }
            return Optional.of(inDb);
        } else {
            biFunction.apply(input, ZoneRetrieveState.ERROR);
            return Optional.empty();
        }
    }
}
