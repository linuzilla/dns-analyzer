package ncu.cc.digger.services;

import ncu.cc.commons.utils.ReadfileUtil;
import ncu.cc.commons.utils.StringUtil;
import ncu.cc.digger.constants.DigConstants;
import ncu.cc.digger.constants.ValueConstants;
import ncu.cc.digger.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class EDNSComplianceTestServiceImpl implements EDNSComplianceTestService {
    private static final Logger logger = LoggerFactory.getLogger(EDNSComplianceTestServiceImpl.class);

    private final EDNSComplianceTestModel testModel;
    private final DigWithCacheService digWithCacheService;

    private static class EDNSTestTarget {
        private final String zone;
        private final String server;
        private final EDNSComplianceTestModel.TestTemplate testTemplate;
        private final EDNSComplianceSingleTestResult testResult;

        private void reportErrorByStatus(EDNSStatus ednsStatus) {
            this.testResult.getErrors().add(ednsStatus.getValue());
        }

        private void reportErrorByValue(String message) {
            this.testResult.getErrors().add(message);
        }

        private void matchStatus(DigResponseStatus expected, DigResult digResult) {
            if (!expected.name().equals(digResult.getStatus())) {
                if (digResult.getStatus() != null) {
                    reportErrorByValue(digResult.getStatus().toLowerCase());
                } else if (digResult.getFailedType().isFailed()) {
                    reportErrorByValue(digResult.getFailedType().getValue());
                } else if (digResult.getFailedMessage() != null) {
                    reportErrorByValue(digResult.getFailedMessage());
                } else {
                    reportErrorByValue("Failed");
                }
            }
        }

        private void checkNOERROR(DigResult digResult) {
            matchStatus(DigResponseStatus.NOERROR, digResult);
        }

        private void checkBADVERS(DigResult digResult) {
            matchStatus(DigResponseStatus.BADVERS, digResult);
        }

        private void checkSOA(DigResult digResult) {
            var success = digResult.getAnswerRecords().stream()
                    .anyMatch(resourceRecord -> RecordTypes.SOA.name().equals(resourceRecord.getRecordType()));

            if (!success) {
                if (DigResponseStatus.NOERROR.name().equals(digResult.getStatus())) {
                    reportErrorByStatus(EDNSStatus.NO_SOA);
                } else {
                    matchStatus(DigResponseStatus.NOERROR, digResult);
                }
            }
        }

        private void checkOptVersionZero(DigResult digResult) {
            if (!digResult.isHavePseudoSection()) {
                reportErrorByStatus(EDNSStatus.NO_OPT);
            } else if (digResult.getVersion() == null || digResult.getVersion() != 0) {
                reportErrorByStatus(EDNSStatus.VERSION_NOT_ZERO);
            }
        }

        private void checkEdnsOverIPv6(DigResult digResult) {
            try {
                InetAddress address = InetAddress.getByName(server);

                if (address instanceof Inet6Address) {
                }
            } catch (UnknownHostException e) {
            }
        }

        private void checkNoSOA(DigResult digResult) {
            if (digResult.getAnswerRecords().stream()
                    .anyMatch(resourceRecord -> RecordTypes.SOA.name().equals(resourceRecord.getRecordType()))) {
                reportErrorByStatus(EDNSStatus.HAVE_SOA);
            }
        }

        private void checkUDPSizeLE512(DigResult digResult) {
            if (digResult.getMessageSize() > 512) {
                reportErrorByStatus(EDNSStatus.OVER512);
            }
        }

        private void checkOptNotPresent(DigResult digResult) {
            if (digResult.getOpt() != null) {
                reportErrorByStatus(EDNSStatus.ECHOED);
            }
        }

        private void checkDoFlagWhileRRSIG(DigResult digResult) {
            if (!Optional.of(digResult.getAnswerRecords().stream()
                    .noneMatch(resourceRecord -> RecordTypes.RRSIG.name().equals(resourceRecord.getRecordType())))
                    .map(noRRSIG -> noRRSIG || !digResult.getFlags().contains("do"))
                    .get()) {
                reportErrorByStatus(EDNSStatus.DO_REQUIRED);
            }
        }

        private void checkZBitToBeClear(DigResult digResult) {
            if (StringUtil.isNotEmpty(digResult.getMbz())) {
                reportErrorByStatus(EDNSStatus.Z_BIT_TO_BE_CLEAR);
            }
        }

        private Map<EDNSExpects, Consumer<DigResult>> functionMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<>(EDNSExpects.SOA, this::checkSOA),
                new AbstractMap.SimpleEntry<>(EDNSExpects.NOERROR, this::checkNOERROR),
                new AbstractMap.SimpleEntry<>(EDNSExpects.BADVERS, this::checkBADVERS),
                new AbstractMap.SimpleEntry<>(EDNSExpects.OPT_VERSION_ZERO, this::checkOptVersionZero),
                new AbstractMap.SimpleEntry<>(EDNSExpects.EDNS_OVER_IPV6, this::checkEdnsOverIPv6),
                new AbstractMap.SimpleEntry<>(EDNSExpects.NO_SOA, this::checkNoSOA),
                new AbstractMap.SimpleEntry<>(EDNSExpects.UDP_SIZE_LE_512, this::checkUDPSizeLE512),
                new AbstractMap.SimpleEntry<>(EDNSExpects.OPTION_NOT_PRESENT, this::checkOptNotPresent),
                new AbstractMap.SimpleEntry<>(EDNSExpects.DO_FLAG, this::checkDoFlagWhileRRSIG),
                new AbstractMap.SimpleEntry<>(EDNSExpects.Z_BIT_TO_BE_CLEAR, this::checkZBitToBeClear)
        );

        private EDNSTestTarget(String zone, String server, EDNSComplianceTestModel.TestTemplate testTemplate) {
            this.zone = zone;
            this.server = server;
            this.testTemplate = testTemplate;

            String command = testTemplate.getCommand().replace("{zone}", zone).replace("{server}", server);

            this.testResult = new EDNSComplianceSingleTestResult();
            this.testResult.setCommand(command);
            this.testResult.setTag(this.testTemplate.getTag());
        }

        private String getCommand() {
            return this.testResult.getCommand();
        }

        private EDNSComplianceSingleTestResult runTest(DigResult digResult) {
            if (digResult.getFailedType().isFailed()) {
                reportErrorByValue(digResult.getFailedType().getValue());
            } else {
                if (digResult.getFlags().contains(DigConstants.RECURSION_DESIRED)) {
                    reportErrorByStatus(EDNSStatus.RD_FLAG);
                }

                this.testTemplate.getEdnsExpectsList()
                        .forEach(ednsExpects -> {
                            var consumer = functionMap.get(ednsExpects);

                            if (consumer == null) {
                                throw new RuntimeException("function for '" + ednsExpects.name() + "' not defined!");
                            } else {
                                consumer.accept(digResult);
                            }
                        });

                if (DigResponseStatus.NOERROR.name().equals(digResult.getStatus())
                        && !digResult.getFlags().contains(DigConstants.AUTHORITATIVE_ANSWER)) {
//                    logger.info("no aa:  {}", String.join(" ", digResult.getFlags()));
                    reportErrorByStatus(EDNSStatus.NO_AA);
                }

                if (digResult.getCookie() != null && DigConstants.COOKIE_GOOD.equals(digResult.getCookie())) {
                    this.testResult.getExtra().add(EDNSStatus.COOKIE.getValue());
                }

                if (StringUtil.isNotEmpty(digResult.getSubnet())) {
                    this.testResult.getExtra().add(EDNSStatus.SUBNET.getValue());
                }
            }

            this.testResult.setSuccess(this.testResult.getErrors().size() == 0);

            String resultString = this.testResult.isSuccess()
                    ? EDNSStatus.OK.getValue()
                    : String.join(",", this.testResult.getErrors());

            if (this.testResult.getExtra().size() > 0) {
                resultString += "," + String.join(",", this.testResult.getExtra());
            }

            this.testResult.setResult(resultString);

            return this.testResult;
        }
    }

    public EDNSComplianceTestServiceImpl(
            DigWithCacheService digWithCacheService,
            @Value(ValueConstants.EDNS_COMPLIANCE_TEST_TEMPLATES) String templateFile) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(EDNSComplianceTestModel.class));

        this.testModel = yaml.load(ReadfileUtil.readFrom(templateFile));
        this.testModel.getTests().stream()
                .peek(EDNSComplianceTestModel.TestTemplate::retrieveTagFromName)
                .forEach(template -> template.setEdnsExpectsList(template.getExpect().stream()
                        .map(EDNSExpects::byString)
                        .collect(Collectors.toList())));

        this.digWithCacheService = digWithCacheService;
    }

    private Mono<EDNSComplianceSingleTestResult> runEdnsTestTemplate(
            EDNSComplianceTestModel.TestTemplate template,
            String zone, String server) {
        EDNSTestTarget target = new EDNSTestTarget(zone, server, template);

        return digWithCacheService.digWithoutCache(target.getCommand())
                .map(target::runTest);
    }

    @Override
    public Mono<EDNSComplianceTestResult> performEDNSTests(String zone, String server) {
        return Flux.fromIterable(testModel.getTests())
                .flatMap(testTemplate -> runEdnsTestTemplate(testTemplate, zone, server))
                .collectList()
                .map(singleTestResults -> {
                    var result = new EDNSComplianceTestResult();

                    result.setSuccess(singleTestResults.stream()
                            .allMatch(EDNSComplianceSingleTestResult::isSuccess));

                    result.setReport(singleTestResults.stream()
                            .map(r -> String.format("%s=%s", r.getTag(), r.getResult()))
                            .collect(Collectors.joining(" ")));

                    return result;
                });
    }
}
