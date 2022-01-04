package ncu.cc.digger.tasks.impl;

import ncu.cc.digger.services.DNSQueryAndStoreService;
import ncu.cc.digger.tasks.DiggerTask;
import ncu.cc.digger.tasks.DiggerTaskResult;
import ncu.cc.digger.utils.DNSUtil;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DNSZoneCheckingTask implements DiggerTask {
    private static final Logger logger = LoggerFactory.getLogger(DNSZoneCheckingTask.class);

    private final DNSQueryAndStoreService dnsQueryAndStoreService;

    public static class QueryPadLoad {
        private final String queryZone;
        private final String queryFrom;
        private final boolean reload;

        public QueryPadLoad(String queryZone, String queryFrom, boolean reload) {
            this.queryZone = queryZone;
            this.queryFrom = queryFrom;
            this.reload = reload;
        }

        public String getQueryZone() {
            return queryZone;
        }

        public String getQueryFrom() {
            return queryFrom;
        }

        public boolean isReload() {
            return reload;
        }
    }

    public DNSZoneCheckingTask(DNSQueryAndStoreService dnsQueryAndStoreService) {
        this.dnsQueryAndStoreService = dnsQueryAndStoreService;
    }

    @Override
    public DiggerTaskResult runTask(Object padLoad) {
        if (padLoad instanceof QueryPadLoad) {
            var queryPadLoad = (QueryPadLoad) padLoad;
            var zone = DNSUtil.zoneNameNormalize(queryPadLoad.getQueryZone());

            logger.info("run task for zone: {} (reload: {})", zone, queryPadLoad.isReload());

            return new DiggerTaskResult(true,
                    queryPadLoad.isReload()
                            ? dnsQueryAndStoreService.digAndStore(zone, queryPadLoad.getQueryFrom()).block()
                            : dnsQueryAndStoreService.fetchOrDig(zone, queryPadLoad.getQueryFrom()).block());
        }

        return null;
    }

    @Override
    public DiggerBackendWorker.OperationEventEnum workOnEvent() {
        return DiggerBackendWorker.OperationEventEnum.DNS_CHECK_ZONE;
    }
}
