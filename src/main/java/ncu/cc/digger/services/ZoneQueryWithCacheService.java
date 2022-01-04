package ncu.cc.digger.services;

import ncu.cc.digger.entities.ReportEntity;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ZoneQueryWithCacheService {
    enum ZoneRetrieveState {
        EXACT, FOUND, BELONGING, ERROR
    }
    interface ZoneBeforeAnalysis {
        String belongingZoneName();
        void triggerAnalyzer(String remoteAddress, boolean reload);
        <R> R map(Function<ZoneBeforeAnalysis, ? extends R> mapper);
    }

    interface ZoneInDatabase {
        ZoneInDatabase ifAlreadyInDatabase(Consumer<ReportEntity> consumer);
        ZoneBeforeAnalysis ifNotInDatabase(Consumer<ZoneBeforeAnalysis> consumer);
    }

    Optional<ZoneInDatabase> findZone(String zone, BiFunction<String,ZoneRetrieveState, Boolean> biFunction);
}
