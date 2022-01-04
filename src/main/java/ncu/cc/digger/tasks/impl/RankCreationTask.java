package ncu.cc.digger.tasks.impl;

import ncu.cc.commons.utils.ByteUtil;
import ncu.cc.digger.constants.Constants;
import ncu.cc.digger.constants.ScoringConstant;
import ncu.cc.digger.entities.CountryRankingEntity;
import ncu.cc.digger.entities.RankingEventEntity;
import ncu.cc.digger.models.statistics.GroupByByteBoolean;
import ncu.cc.digger.repositories.CountryRankingRepository;
import ncu.cc.digger.repositories.RankingEventRepository;
import ncu.cc.digger.repositories.UnivReportViewRepository;
import ncu.cc.digger.services.CountryCodeService;
import ncu.cc.digger.tasks.DiggerTask;
import ncu.cc.digger.tasks.DiggerTaskResult;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class RankCreationTask implements DiggerTask {
    private static final Logger logger = LoggerFactory.getLogger(RankCreationTask.class);

    private final RankingEventRepository rankingEventRepository;
    private final CountryRankingRepository countryRankingRepository;
    private final CountryCodeService countryCodeService;
    private final UnivReportViewRepository univReportViewRepository;

    public RankCreationTask(RankingEventRepository rankingEventRepository, CountryRankingRepository countryRankingRepository, CountryCodeService countryCodeService, UnivReportViewRepository univReportViewRepository) {
        this.rankingEventRepository = rankingEventRepository;
        this.countryRankingRepository = countryRankingRepository;
        this.countryCodeService = countryCodeService;
        this.univReportViewRepository = univReportViewRepository;
    }

    private int percentage(List<? extends GroupByByteBoolean> booleanList) {
        AtomicLong total = new AtomicLong(0L);
        AtomicLong trueCounter = new AtomicLong(0L);
        booleanList.stream()
                .peek(entry -> total.addAndGet(entry.getCnt()))
                .filter(entry -> entry.getBoolean() == ByteUtil.TRUE)
                .forEach(entry -> trueCounter.addAndGet(entry.getCnt()));

        // logger.info("{} : {} / {}", booleanList.get(0).getClass().getName(), trueCounter.get(), total.get());

        return total.get() > 0L ? (int) (trueCounter.get() * ScoringConstant.BASE_SCORE / total.get()) : 0;
    }

    private int calculateScore(CountryRankingEntity entity, long total) {
        return entity.getNormal() * ScoringConstant.NORMAL_SCORE +
                entity.getInfo() * ScoringConstant.INFO_SCORE +
                entity.getLow() * ScoringConstant.LOW_SCORE +
                entity.getMedium() * ScoringConstant.MEDIUM_SCORE +
                entity.getHigh() * ScoringConstant.HIGH_SCORE +
                entity.getUrgent() * ScoringConstant.URGENT_SCORE;
    }

    private CountryRankingEntity calculator(int eventId, String countryCode) {
        CountryRankingEntity entity = new CountryRankingEntity();
        entity.setEventId(eventId);
        entity.setCountryCode(countryCode);

//        univReportViewRepository.findNumberOfProblemsStatistics(countryCode);

        AtomicLong count = new AtomicLong(0L);
        long[] severity = new long[]{0, 0, 0, 0, 0, 0};

        univReportViewRepository.findSeverityLevelStatistics(countryCode)
                .forEach(severityLevelStatistics -> {
                    int level = (int) severityLevelStatistics.getSeverityLevel();

                    if (level >= 0 && level <= 5) {
                        severity[level] = severityLevelStatistics.getCnt();
                        count.addAndGet(severityLevelStatistics.getCnt());
                    }
                });

        entity.setZones((int) count.get());

        if (count.get() >= ScoringConstant.MIN_SCORING_ZONE) {
            logger.info("Country: {}", countryCode);
            entity.setDnssec(percentage(univReportViewRepository.findDnssecEnabledStatistics(countryCode)));
            entity.setIpv6(percentage(univReportViewRepository.findIPv6AvailableStatistics(countryCode)));
            entity.setEdns(percentage(univReportViewRepository.findNonCompliantEdnsStatistics(countryCode)));
            entity.setAxfr(percentage(univReportViewRepository.findOpenAxfrStatistics(countryCode)));
            entity.setRecursion(percentage(univReportViewRepository.findOpenRecursiveStatistics(countryCode)));
            entity.setRrset(percentage(univReportViewRepository.findRrsetInconsistencyStatistics(countryCode)));

            entity.setNormal((int) (severity[0] * ScoringConstant.BASE_SCORE / count.get()));
            entity.setInfo((int) (severity[1] * ScoringConstant.BASE_SCORE / count.get()));
            entity.setLow((int) (severity[2] * ScoringConstant.BASE_SCORE / count.get()));
            entity.setMedium((int) (severity[3] * ScoringConstant.BASE_SCORE / count.get()));
            entity.setHigh((int) (severity[4] * ScoringConstant.BASE_SCORE / count.get()));
            entity.setUrgent((int) (severity[5] * ScoringConstant.BASE_SCORE / count.get()));

            entity.setScore(calculateScore(entity, count.get()));
        }

        return entity;
    }

    @Override
    public DiggerTaskResult runTask(Object padLoad) {
        RankingEventEntity eventEntity = new RankingEventEntity();

        Date now = Calendar.getInstance().getTime();
        eventEntity.setTag(Constants.DATE_FORMAT.format(now));
        eventEntity.setName("Ranking at " + Constants.DATETIME_FORMAT.format(now));

        eventEntity.setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        int eventId = rankingEventRepository.save(eventEntity).getId();
        AtomicInteger rank = new AtomicInteger(0);

        Timestamp currentTimeStamp = new Timestamp(now.getTime());

        List<CountryRankingEntity> entities = countryCodeService.getCountries().keySet()
                .stream()
                .map(countryCode -> calculator(eventId, countryCode))
                .filter(entity -> entity.getZones() >= ScoringConstant.MIN_SCORING_ZONE)
                .sorted((o1, o2) -> o2.getScore() - o1.getScore())
                .peek(entity -> entity.setRank(rank.incrementAndGet()))
                .peek(entity -> entity.setCreatedAt(currentTimeStamp))
                .collect(Collectors.toList());

        this.countryRankingRepository.saveAll(entities);

        return new DiggerTaskResult(true, padLoad);
    }

    @Override
    public DiggerBackendWorker.OperationEventEnum workOnEvent() {
        return DiggerBackendWorker.OperationEventEnum.RANK_CREATION;
    }
}
