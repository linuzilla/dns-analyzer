package ncu.cc.digger.controllers;

import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.entities.ReportEntity;
import ncu.cc.digger.repositories.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.REPORT)
public class ReportController {
    private final ReportRepository reportRepository;

    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping("/{pageNo}")
    public Page<ReportEntity> findPage(@PathVariable("pageNo") Integer pageNo) {
        PageRequest pageable = PageRequest.of(pageNo, 50);
        return reportRepository.findAllByOrderByUpdatedAtDesc(pageable);
    }
}
