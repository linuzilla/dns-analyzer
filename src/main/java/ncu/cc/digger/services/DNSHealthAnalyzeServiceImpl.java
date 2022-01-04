package ncu.cc.digger.services;

import ncu.cc.commons.utils.ObjectAccessor;
import ncu.cc.commons.utils.ReadfileUtil;
import ncu.cc.digger.constants.LocaleEnum;
import ncu.cc.digger.constants.ValueConstants;
import ncu.cc.digger.models.*;
import ncu.cc.digger.models.dns.FlatResourceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DNSHealthAnalyzeServiceImpl implements DNSHealthAnalyzeService {
    private static final Logger logger = LoggerFactory.getLogger(DNSHealthAnalyzeServiceImpl.class);

    private final LocalizeProblem[] localizeProblem;
    private final LocaleService localeService;

    public DNSHealthAnalyzeServiceImpl(
            LocaleService localeService,
            @Value(ValueConstants.PROBLEM_SPEC_EN_US) String enUsTemplateFile,
            @Value(ValueConstants.PROBLEM_SPEC_ZH_TW) String zhTwTemplateFile) throws FileNotFoundException {

        this.localeService = localeService;
        this.localizeProblem = new LocalizeProblem[2];
        this.loadLocalizeProblem(enUsTemplateFile, LocaleEnum.EN_US);
        this.loadLocalizeProblem(zhTwTemplateFile, LocaleEnum.ZH_TW);
    }

    private void loadLocalizeProblem(String templateFile, LocaleEnum localeEnum) throws FileNotFoundException {
        var yaml = new Yaml(new Constructor(LocalizeProblem.class));
        this.localizeProblem[localeEnum.getCode()] = yaml.load(ReadfileUtil.readFrom(templateFile));

        this.localizeProblem[localeEnum.getCode()].getProblems().forEach((tag, problem) ->
                problem.setSeverityLevel(SeverityLevel.byName(problem.getSeverity()).orElse(SeverityLevel.URGENT)));
    }

    private static void iterateAllAndFilter(
            DNSProblemsEnum problem,
            DNSHealthCheckResult healthCheckResult,
            Predicate<FlatResourceRecord> predicate) {
        healthCheckResult.getAllNameServers()
                .stream()
                .filter(predicate)
                .forEach(flatResourceRecord -> {
                    healthCheckResult.getProblems().add(
                            new DNSProblem(problem, healthCheckResult.getZone(),
                                    flatResourceRecord, null, flatResourceRecord.getError())
                    );
                });
    }

    public static void childrenRRSetDisagreements(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        AtomicInteger count = new AtomicInteger(0);

        if (healthCheckResult.getDaughterRRSets()
                .stream()
                .map(daughterRRSet -> daughterRRSet.getNameServers().size())
                .filter(integer -> integer > 0)
                .peek(count::set)
                .anyMatch(integer -> integer != count.get())) {
            healthCheckResult.getProblems().add(new DNSProblem(problem, healthCheckResult.getZone()));
        }
    }

    public static void childrenDefinedNoNSRrset(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        if (healthCheckResult.getCommons().size() == 0 && healthCheckResult.getDaughters().size() == 0) {
            healthCheckResult.getProblems().add(new DNSProblem(problem, healthCheckResult.getZone()));
        } else {
            if (healthCheckResult.getDaughters().size() > 0) {
                healthCheckResult.getProblems().add(new DNSProblem(DNSProblemsEnum.CHILD_HAS_MORE_RR_THAN_PARENT, healthCheckResult.getZone()));
            }

            if (healthCheckResult.getParents().size() > 0) {
                healthCheckResult.getProblems().add(new DNSProblem(DNSProblemsEnum.PARENT_HAS_MORE_RR_THAN_CHILD, healthCheckResult.getZone()));
            }
        }
    }
//
//    public static void childHasMoreRRThanParent(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
//        if (healthCheckResult.getDaughters().size() > 0) {
//            healthCheckResult.getProblems().add(new DNSProblem(problem, healthCheckResult.getZone()));
//        }
//    }
//
//    public static void parentHasMoreRRThanChild(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
//        if (healthCheckResult.getParents().size() > 0) {
//            healthCheckResult.getProblems().add(new DNSProblem(problem, healthCheckResult.getZone()));
//        }
//    }

    public static void nameServerNotResponding(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        iterateAllAndFilter(
                problem,
                healthCheckResult,
                flatResourceRecord -> flatResourceRecord.isResponding() != null && !flatResourceRecord.isResponding()
        );
    }

    public static void nameServerNotWorkingProperly(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        iterateAllAndFilter(
                problem,
                healthCheckResult,
                flatResourceRecord -> flatResourceRecord.isResponding() != null
                        && flatResourceRecord.isResponding()
                        && flatResourceRecord.getSoa() == null
        );
    }

    public static void nameServerAddressNotFound(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        iterateAllAndFilter(
                problem,
                healthCheckResult,
                flatResourceRecord -> flatResourceRecord.getAddress() == null);
    }

    public static void noWorkingNameServer(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        if (healthCheckResult.getAllNameServers().stream()
                .noneMatch(flatResourceRecord ->
                        flatResourceRecord.isResponding() == null || flatResourceRecord.isResponding())) {
            healthCheckResult.getProblems().add(
                    new DNSProblem(problem, healthCheckResult.getZone())
            );
        } else {
            noWorkingIPv4Server(DNSProblemsEnum.NO_WORKING_IPV4_NAME_SERVER, healthCheckResult);
            noWorkingIPv6Server(DNSProblemsEnum.NO_WORKING_IPV6_NAME_SERVER, healthCheckResult);
        }
    }

    private static void noWorkingIPv4Server(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        if (healthCheckResult.getAllNameServers().stream()
                .filter(FlatResourceRecord::isIPv4)
                .noneMatch(flatResourceRecord ->
                        flatResourceRecord.isResponding() == null || flatResourceRecord.isResponding())) {
            healthCheckResult.getProblems().add(
                    new DNSProblem(problem, healthCheckResult.getZone())
            );
        }
    }

    private static void noWorkingIPv6Server(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        if (healthCheckResult.getAllNameServers().stream()
                .filter(FlatResourceRecord::isIs_v6)
                .noneMatch(flatResourceRecord ->
                        flatResourceRecord.isResponding() == null || flatResourceRecord.isResponding())) {
            healthCheckResult.getProblems().add(
                    new DNSProblem(problem, healthCheckResult.getZone())
            );
        }
    }

    public static void zoneNotExists(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        if (healthCheckResult.getParentNameServers().size() > 0
                && healthCheckResult.getNumberOfServers() == 0) {
            healthCheckResult.getProblems().add(
                    new DNSProblem(DNSProblemsEnum.ZONE_NOT_EXISTS, healthCheckResult.getZone())
            );
            healthCheckResult.setSeverityLevel(SeverityLevel.FATAL);
        }
    }

    public static void parentZoneNotExists(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        if (healthCheckResult.getParentNameServers().size() == 0) {
            healthCheckResult.getProblems().add(
                    new DNSProblem(DNSProblemsEnum.PARENT_ZONE_NOT_EXISTS, healthCheckResult.getZone())
            );
            healthCheckResult.setSeverityLevel(SeverityLevel.FATAL);
        }
    }

    public static void onlyOneNameServer(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        if (healthCheckResult.getCommons().size() + healthCheckResult.getParents().size() == 1) {
            healthCheckResult.getProblems().add(
                    new DNSProblem(healthCheckResult.getDaughters().size() == 0
                            ? DNSProblemsEnum.ONLY_ONE_NAME_SERVER_IN_CONFIGURATION
                            : DNSProblemsEnum.ONLY_ONE_NAME_SERVER_IN_PARENT, healthCheckResult.getZone(),
                            healthCheckResult.getParents().size() == 1
                                    ? healthCheckResult.getParents().get(0)
                                    : healthCheckResult.getCommons().get(0),
                            null,
                            null)
            );
        }
    }

    public static void findSoaInconsistency(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        healthCheckResult.getAllNameServers()
                .stream()
                .filter(flatResourceRecord -> flatResourceRecord.getSoa() != null)
                .findFirst()
                .ifPresent(firstFlatResourceRecord -> {
                    var first = firstFlatResourceRecord.getSoa();

                    if (healthCheckResult.getAllNameServers()
                            .stream()
                            .filter(flatResourceRecord -> flatResourceRecord.getSoa() != null)
                            .anyMatch(flatResourceRecord -> {
                                var another = flatResourceRecord.getSoa();

                                if (first.getSerial() != another.getSerial() ||
                                        !first.getEmail().equals(another.getEmail())) {
                                    healthCheckResult.getProblems().add(
                                            new DNSProblem(
                                                    problem,
                                                    healthCheckResult.getZone(),
                                                    null,
                                                    String.format("SerialNo: %d, Email: %s (offered by: %s %s)<br />" +
                                                                    "SerialNo: %d, Email: %s (offered by: %s %s)",
                                                            first.getSerial(),
                                                            first.getEmail(),
                                                            firstFlatResourceRecord.getName(),
                                                            firstFlatResourceRecord.getAddress(),
                                                            another.getSerial(),
                                                            another.getEmail(),
                                                            flatResourceRecord.getName(),
                                                            flatResourceRecord.getAddress()),
                                                    null
                                            )
                                    );

                                    return true;
                                } else {
                                    return false;
                                }
                            })) {
                        healthCheckResult.setSoaInconsistency(true);
                    }
                });
    }

    public static void ednsComplianceError(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        healthCheckResult.getAllNameServers()
                .stream()
                .filter(flatResourceRecord -> flatResourceRecord.getEdns() != null && !flatResourceRecord.getEdns().isSuccess())
                .forEach(flatResourceRecord -> {
                    healthCheckResult.getProblems().add(
                            new DNSProblem(
                                    problem,
                                    healthCheckResult.getZone(),
                                    flatResourceRecord,
                                    "<b>EDNS Test Result</b><br /><pre>" +
                                            flatResourceRecord.getEdns().getReport().replace(" ", "\n") + "</pre>",
                                    null)
                    );
                    healthCheckResult.setNonCompliantEdns(true);
                });
    }

    public static void dnssecNotEnable(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        if (!healthCheckResult.isDnssecEnabled()) {
            healthCheckResult.getProblems().add(
                    new DNSProblem(problem, healthCheckResult.getZone())
            );
        }
    }

    public static void allowOpenRecursive(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        healthCheckResult.getAllNameServers()
                .stream()
                .filter(FlatResourceRecord::isAllowRecursive)
                .forEach(flatResourceRecord -> {
                    healthCheckResult.getProblems().add(
                            new DNSProblem(problem, healthCheckResult.getZone(), flatResourceRecord)
                    );
                    healthCheckResult.setOpenRecursive(true);
                });
    }

    public static void allowOpenZoneTransfer(DNSProblemsEnum problem, DNSHealthCheckResult healthCheckResult) {
        healthCheckResult.getAllNameServers()
                .stream()
                .filter(FlatResourceRecord::isAllowTransfer)
                .forEach(flatResourceRecord -> {
                    healthCheckResult.getProblems().add(
                            new DNSProblem(problem, healthCheckResult.getZone(), flatResourceRecord)
                    );
                    healthCheckResult.setOpenAxfr(true);
                });
    }

    private Map<String, LocalizeProblem.ProblemSpec> getProblemSpec() {
        return this.localizeProblem[LocaleEnum.toLocale(localeService.currentLocale().toString()).getCode()]
                .getProblems();
    }

    @Override
    public void analyze(DNSHealthCheckResult healthCheckResult) {
        List.of(DNSProblemsEnum.values())
                .forEach(dnsProblem -> dnsProblem.accept(healthCheckResult));

        var severityLevelCounter = List.of(SeverityLevel.values()).stream()
                .collect(Collectors.toMap(Function.identity(), severityLevel -> new AtomicInteger(0)));


        if (healthCheckResult.getSeverityLevel() == null) {
            healthCheckResult.setSeverityLevel(SeverityLevel.NORMAL);
        }


        healthCheckResult.getProblems()
                .forEach(dnsProblem -> {
                    logger.trace("check against: {}", dnsProblem.getProblem().name());
                    LocalizeProblem.ProblemSpec problemSpec = this.getProblemSpec().get(dnsProblem.getProblem().name());

                    if (problemSpec == null) {
                        logger.error("Problem '{}' not defined!", dnsProblem.getProblem().name());
                    } else {
                        dnsProblem.setSeverityLevel(problemSpec.getSeverityLevel());
                        severityLevelCounter.get(problemSpec.getSeverityLevel()).incrementAndGet();
                    }
                });

        var severity = new DNSHealthCheckResult.Severity();
        var objectAccessor = new ObjectAccessor<>(severity);
        var maxLevel = new AtomicInteger(healthCheckResult.getSeverityLevel().getLevel());

        severityLevelCounter.forEach((severityLevel, atomicInteger) -> {
            try {
                if (atomicInteger.get() > 0 && severityLevel.getLevel() > maxLevel.get()) {
                    maxLevel.set(severityLevel.getLevel());
                }
                objectAccessor.set(severityLevel.name().toLowerCase(), atomicInteger.get());
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
            }
        });

        healthCheckResult.setSeverity(severity);

        healthCheckResult.setSeverityLevel(SeverityLevel.byLevel(maxLevel.get()).orElse(SeverityLevel.URGENT));
        healthCheckResult.setNumberOfProblems(healthCheckResult.getProblems().size());
        logger.info("Zone: {}, Severity: {}", healthCheckResult.getZone(), healthCheckResult.getSeverityLevel().name());
    }

    @Override
    public List<DNSProblemReport> problemsAndSuggestions(DNSHealthCheckResult healthCheckResult) {
        return healthCheckResult.getProblems().stream()
                .map(dnsProblem -> {
                    LocalizeProblem.ProblemSpec problemSpec = this.getProblemSpec().get(
                            dnsProblem.getProblem().name()
                    );

                    if (problemSpec != null) {
                        var problem = problemSpec.getProblem()
                                .replace("{zone}", dnsProblem.getZone());

                        if (dnsProblem.getNameServer() != null) {
                            if (dnsProblem.getNameServer().getAddress() != null) {
                                problem = problem.replace("{addr}", dnsProblem.getNameServer().getAddress());
                            } else {
                                problem = problem.replace("{addr}", "address not exists");
                            }

                            if (dnsProblem.getNameServer().getName() != null) {
                                problem = problem.replace("{domain}", dnsProblem.getNameServer().getName());
                            }
                        }

                        if (dnsProblem.getError() != null) {
                            problem = problem.replace("{error}", dnsProblem.getError());
                        }

                        return new DNSProblemReport(
                                dnsProblem.getSeverityLevel().getLevel(),
                                problem, problemSpec.getRecommendation(),
                                dnsProblem.getAdditionalInfo());
                    } else {
                        logger.info("problem spec '{}' not found", dnsProblem.getProblem().name());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .sorted((o1, o2) -> o2.getSeverity() - o1.getSeverity())
                .collect(Collectors.toList());
    }
}
