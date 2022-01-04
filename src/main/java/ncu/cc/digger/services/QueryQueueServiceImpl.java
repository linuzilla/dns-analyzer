package ncu.cc.digger.services;

import ncu.cc.digger.entities.QueryQueueEntity;
import ncu.cc.digger.repositories.QueryQueueRepository;
import ncu.cc.digger.repositories.ReportRepository;
import ncu.cc.digger.utils.DNSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Service
public class QueryQueueServiceImpl implements QueryQueueService {
    private final Logger logger = LoggerFactory.getLogger(QueryQueueServiceImpl.class);
    private final QueryQueueRepository queryQueueRepository;
    private final ReportRepository reportRepository;
    private final AtomicLong idleCounter = new AtomicLong(0L);

    public QueryQueueServiceImpl(QueryQueueRepository queryQueueRepository, ReportRepository reportRepository) {
        this.queryQueueRepository = queryQueueRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public AddQueueStatus addQueue(String input) {
        Optional<String> optionalZone = DNSUtil.verifyOrRetrieveZone(input);

        if (optionalZone.isPresent()) {
            Optional<QueryQueueEntity> queueEntityOptional = queryQueueRepository.findById(optionalZone.get());

            if (queueEntityOptional.isEmpty()) {
                QueryQueueEntity queryQueueEntity = new QueryQueueEntity();

                queryQueueEntity.setZoneId(optionalZone.get());
                queryQueueEntity.setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                queryQueueEntity.setServiceAt(null);

                queryQueueRepository.save(queryQueueEntity);
                logger.info("addQueue: {} added", optionalZone.get());
                return AddQueueStatus.ADDED;
            } else if (queueEntityOptional.get().getServiceAt() == null) {
                logger.info("addQueue: {} already exists and waiting to be served", optionalZone.get());
                return AddQueueStatus.ON_WAITING;
            } else {
                logger.info("addQueue: {} already exists and been served", optionalZone.get());
                return AddQueueStatus.ALREADY_SERVED;
            }
        } else {
            logger.info("addQueue: {} format error", input);
            return AddQueueStatus.FORMAT_ERROR;
        }
    }

    @Override
    public void retrieveAndServe(Function<String, QueueServiceResult> function) {
        long counter = this.idleCounter.incrementAndGet();
        boolean findNext;

        do {
            QueueServiceResult queueServiceResult = QueueServiceResult.UNKNOWN;
            findNext = false;
            Optional<QueryQueueEntity> first = queryQueueRepository.findFirstByServiceAtIsNullOrderByCreatedAt();

            if (first.isPresent()) {
                if (reportRepository.findById(first.get().getZoneId()).isPresent()) {
                    logger.info("retrieveAndServe: {} already have report ... find next", first.get().getZoneId());
                    findNext = true;
                } else {
                    queueServiceResult = function.apply(first.get().getZoneId());

                    logger.info("retrieveAndServed: {}, result: {}", first.get().getZoneId(), queueServiceResult);
                    idleCounter.set(0L);
                }

                if (queueServiceResult != QueueServiceResult.BUSY) {
                    first.get().setServiceAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                    queryQueueRepository.save(first.get());
                }
            } else if (counter % 100L == 1) {
                logger.info("no job found");
            }
        } while (findNext);
    }
}
