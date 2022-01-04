package ncu.cc.digger.models;

import ncu.cc.digger.services.DNSHealthAnalyzeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

public enum DNSProblemsEnum {
    CHILDREN_RR_RECORD_DISAGREEMENTS(DNSHealthAnalyzeServiceImpl::childrenRRSetDisagreements),
    CHILDREN_DEFINED_NO_NS_RRSET(DNSHealthAnalyzeServiceImpl::childrenDefinedNoNSRrset),
    CHILD_HAS_MORE_RR_THAN_PARENT(DNSProblemsEnum::byPassOrServedByOther),
    PARENT_HAS_MORE_RR_THAN_CHILD(DNSProblemsEnum::byPassOrServedByOther),
    NAME_SERVER_NOT_RESPONDING(DNSHealthAnalyzeServiceImpl::nameServerNotResponding),
    NAME_SERVER_NOT_WORKING_PROPERLY(DNSHealthAnalyzeServiceImpl::nameServerNotWorkingProperly),
    NAME_SERVER_ADDRESS_NOT_FOUND(DNSHealthAnalyzeServiceImpl::nameServerAddressNotFound),
    NO_WORKING_NAME_SERVER(DNSHealthAnalyzeServiceImpl::noWorkingNameServer),
    NO_WORKING_IPV4_NAME_SERVER(DNSProblemsEnum::byPassOrServedByOther),
    NO_WORKING_IPV6_NAME_SERVER(DNSProblemsEnum::byPassOrServedByOther),
    ONLY_ONE_NAME_SERVER_IN_CONFIGURATION(DNSProblemsEnum::byPassOrServedByOther),
    ONLY_ONE_NAME_SERVER_IN_PARENT(DNSHealthAnalyzeServiceImpl::onlyOneNameServer),
    ZONE_NOT_EXISTS(DNSHealthAnalyzeServiceImpl::zoneNotExists),
    PARENT_ZONE_NOT_EXISTS(DNSHealthAnalyzeServiceImpl::parentZoneNotExists),
    SOA_DIFFERENT_BETWEEN_SERVERS(DNSHealthAnalyzeServiceImpl::findSoaInconsistency),
    ALLOW_OPEN_RECURSIVE(DNSHealthAnalyzeServiceImpl::allowOpenRecursive),
    ALLOW_OPEN_ZONE_TRANSFER(DNSHealthAnalyzeServiceImpl::allowOpenZoneTransfer),
    EDNS_COMPLIANCE_ERROR(DNSHealthAnalyzeServiceImpl::ednsComplianceError),
    EDNS_COMPLIANCE_WARNING,
    DNS_COOKIE_NOT_SUPPORT,
    DNS_SUBNET_NOT_SUPPORT,
    DNSSEC_NOT_ENABLE(DNSHealthAnalyzeServiceImpl::dnssecNotEnable);

    private static final Logger logger = LoggerFactory.getLogger(DNSProblemsEnum.class);
    private final BiConsumer<DNSProblemsEnum, DNSHealthCheckResult> consumer;

    public static void notImplementedYet(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
    }

    public static void byPassOrServedByOther(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
    }

    DNSProblemsEnum(BiConsumer<DNSProblemsEnum, DNSHealthCheckResult> consumer) {
        this.consumer = consumer;
    }

    DNSProblemsEnum() {
        this.consumer = DNSProblemsEnum::notImplementedYet;
    }

    public void accept(DNSHealthCheckResult healthCheckResult) {
        consumer.accept(this, healthCheckResult);
    }
}
