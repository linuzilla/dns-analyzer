package ncu.cc.digger.services;

import java.util.function.Function;

public interface QueryQueueService {
    AddQueueStatus addQueue(String input);

    void retrieveAndServe(Function<String, QueueServiceResult> function);

    enum AddQueueStatus {
        ADDED, ON_WAITING, ALREADY_SERVED, FORMAT_ERROR
    }

    enum QueueServiceResult {
        UNKNOWN, SERVED, BUSY, NOT_FOUND, SERVED_BEFORE, FORMAT_ERROR
    }
}
