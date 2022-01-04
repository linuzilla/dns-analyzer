package ncu.cc.digger.converters;

import ncu.cc.commons.utils.ByteUtil;
import ncu.cc.digger.entities.ReportEntity;
import ncu.cc.digger.entities.ReportHistoryEntity;
import ncu.cc.digger.models.DNSHealthCheckResult;
import ncu.cc.digger.utils.DNSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class HealthResultToEntity {
    private static final Logger logger = LoggerFactory.getLogger(HealthResultToEntity.class);

    public static ReportEntity healthResultToReport(DNSHealthCheckResult healthCheckResult, String queryFrom) {
        ReportEntity reportEntity = new ReportEntity();

        reportEntity.setJsonReport(healthCheckResult.toJSON());

        reportEntity.setZoneId(DNSUtil.stripTrailingDot(healthCheckResult.getZone()));
        reportEntity.setParentZone(DNSUtil.stripTrailingDot(healthCheckResult.getParentZone()));

        reportEntity.setDnssecEnabled(ByteUtil.byteBoolean(healthCheckResult.isDnssecEnabled()));
        reportEntity.setIpv6Available(ByteUtil.byteBoolean(healthCheckResult.isIpv6Available()));
        reportEntity.setNonCompliantEdns(ByteUtil.byteBoolean(healthCheckResult.isNonCompliantEdns()));
        reportEntity.setOpenAxfr(ByteUtil.byteBoolean(healthCheckResult.isOpenAxfr()));
        reportEntity.setOpenRecursive(ByteUtil.byteBoolean(healthCheckResult.isOpenRecursive()));
        reportEntity.setRrsetInconsistency(ByteUtil.byteBoolean(healthCheckResult.isRrsetInconsistency()));
        reportEntity.setServerNotWorking(ByteUtil.byteBoolean(healthCheckResult.isServerNotWorking()));
        reportEntity.setSoaInconsistency(ByteUtil.byteBoolean(healthCheckResult.isSoaInconsistency()));

        reportEntity.setNumberOfProblems(healthCheckResult.getNumberOfProblems());
        reportEntity.setNumberOfServers(healthCheckResult.getNumberOfServers());

        reportEntity.setSeverityUrgent(healthCheckResult.getSeverity().urgent);
        reportEntity.setSeverityHigh(healthCheckResult.getSeverity().high);
        reportEntity.setSeverityMedium(healthCheckResult.getSeverity().medium);
        reportEntity.setSeverityLow(healthCheckResult.getSeverity().low);
        reportEntity.setSeverityInfo(healthCheckResult.getSeverity().info);

        reportEntity.setSoaEmail(healthCheckResult.getSoaEmail() == null ? "" : healthCheckResult.getSoaEmail());
        if (healthCheckResult.getSoaSerialNo() != null) {
            reportEntity.setSoaSerialno((long) healthCheckResult.getSoaSerialNo());
        } else {
            reportEntity.setSoaSerialno(0L);
        }

        reportEntity.setRemoteAddress(queryFrom);

        reportEntity.setSeverityLevel((byte) healthCheckResult.getSeverityLevel().getLevel());

        return reportEntity;
    }

    public static DNSHealthCheckResult reportToHealthResult(ReportEntity reportEntity) {
        return DNSHealthCheckResult.fromJSON(reportEntity.getJsonReport());
    }

    public static ReportHistoryEntity healthResultToReportHistory(DNSHealthCheckResult healthCheckResult, String queryFrom) {
        ReportHistoryEntity entity = new ReportHistoryEntity();

        BeanUtils.copyProperties(healthResultToReport(healthCheckResult, queryFrom), entity);

        return entity;
    }
}
