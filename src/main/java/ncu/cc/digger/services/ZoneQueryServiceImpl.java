package ncu.cc.digger.services;

import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.entities.ReportEntity;
import ncu.cc.digger.repositories.ReportRepository;
import ncu.cc.digger.sessionbeans.AnonymousUserSession;
import ncu.cc.digger.tasks.impl.DNSZoneCheckingTask;
import ncu.cc.digger.utils.DNSUtil;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ZoneQueryServiceImpl implements ZoneQueryService {
    private static final Logger logger = LoggerFactory.getLogger(ZoneQueryServiceImpl.class);

    private final ReportRepository reportRepository;
    private final DiggerBackendWorker diggerBackendWorker;
    private final Set<String> zoneInQuery = new HashSet<>();

    public ZoneQueryServiceImpl(ReportRepository reportRepository, DiggerBackendWorker diggerBackendWorker) {
        this.reportRepository = reportRepository;
        this.diggerBackendWorker = diggerBackendWorker;
    }

    private void trigger(String zone, String queryFrom, boolean reload) {
        if (! zoneInQuery.contains(zone)) {
            this.diggerBackendWorker.triggerEvent(
                    DiggerBackendWorker.OperationEventEnum.DNS_CHECK_ZONE,
                    new DNSZoneCheckingTask.QueryPadLoad(zone, queryFrom, reload),
                    (diggerTaskResult, e) -> zoneInQuery.remove(zone));
        }
    }

    @Override
    public void triggerQueryZone(String queryZone, String queryFrom, boolean reload) {
        String zone = DNSUtil.zoneNameNormalize(queryZone);
//        String zoneWithTrailingDot = DNSUtil.stripTrailingDot(zone);

        this.trigger(zone, queryFrom, reload);

//        Optional<ReportEntity> optional = reportRepository.findById(zone);
//
//        if (! optional.isPresent()) {
//            this.diggerBackendWorker.triggerEvent(
//                    DiggerBackendWorker.OperationEventEnum.DNS_CHECK_ZONE,
//                    zone);
//            logger.info("Trigger DNS Check Zone for {}", zone);
//            return Routes.redirect(Routes.ZONE + "/" + anonymousUserSession.getQueryZone());
//        }
    }
}
