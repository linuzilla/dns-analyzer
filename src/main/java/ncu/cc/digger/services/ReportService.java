package ncu.cc.digger.services;

import ncu.cc.digger.entities.ReportEntity;

import java.util.Optional;

public interface ReportService {
    Optional<ReportEntity> findByZoneId(String zone);
}
