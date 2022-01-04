package ncu.cc.digger.services;

import ncu.cc.digger.entities.ReportEntity;
import ncu.cc.digger.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Optional<ReportEntity> findByZoneId(String zone) {
        return this.reportRepository.findById(zone);
    }
}
