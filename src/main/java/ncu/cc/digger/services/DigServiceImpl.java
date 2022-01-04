package ncu.cc.digger.services;

import ncu.cc.commons.utils.ObjectAccessor;
import ncu.cc.digger.models.DigResult;
import ncu.cc.digger.models.ResourceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DigServiceImpl implements DigService {
    private static final Logger logger = LoggerFactory.getLogger(DigServiceImpl.class);

    private final ReactiveCommandExecutor reactiveCommandExecutor;

    private enum State {
        HEADER_SECTION, OPT_PSEUDOSECTION, ANSWER_SECTION, AUTHORITY_SECTION, ADDITIONAL_SECTION
    }

    private static class DigResultWrapper {
        private final DigResult digResult;
        private final ObjectAccessor<DigResult> objectAccessor;
        private State currentState;

        private DigResultWrapper() {
            this.currentState = State.HEADER_SECTION;
            this.digResult = new DigResult();
            this.objectAccessor = new ObjectAccessor<>(this.digResult);
        }
    }

    private static class MatchedData {
        private final String datum;
        private final MatchRule rule;
        private final Matcher matcher;

        private MatchedData(String datum, MatchRule rule, Matcher matcher) {
            this.datum = datum;
            this.rule = rule;
            this.matcher = matcher;
        }

        private void handle(final DigResultWrapper digResultWrapper) {
            handleVariables(digResultWrapper);
            handleFailedType(digResultWrapper);
            handlePush(digResultWrapper);
            handleGoTo(digResultWrapper);
        }

        private void handleVariables(final DigResultWrapper digResultWrapper) {
            if (rule.variables != null) {
                int index = 1;
                for (String varName : rule.variables) {
                    String value = matcher.group(index);
                    index++;

                    int p = varName.indexOf(':');

                    try {
                        if (p > 0) {
                            var varType = varName.substring(p + 1);
                            varName = varName.substring(0, p);

                            if ("int".equals(varType)) {
                                Integer intValue = Integer.parseInt(value);
                                logger.debug("Set variable: '{}' as '{}'", varName, intValue);
                                digResultWrapper.objectAccessor.set(varName, intValue);
                            }
                        } else {

                            logger.debug("Set variable: '{}' as '{}'", varName, value);
                            digResultWrapper.objectAccessor.set(varName, value);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        private void handleFailedType(final DigResultWrapper digResultWrapper) {
            if (rule.failedType != DigResult.FailedType.UNKNOWN) {
                digResultWrapper.digResult.setFailedType(rule.failedType);
            }
        }

        private void handlePush(final DigResultWrapper digResultWrapper) {
            if (rule.push != null) {
                try {
                    @SuppressWarnings("unchecked")
                    List<ResourceRecord> list = digResultWrapper.objectAccessor.get(rule.push, List.class);

                    list.add(new ResourceRecord(matcher));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void handleGoTo(final DigResultWrapper digResultWrapper) {
            if (rule.gotoState != null) {
                digResultWrapper.currentState = rule.gotoState;

                if (digResultWrapper.currentState == State.OPT_PSEUDOSECTION) {
                    digResultWrapper.digResult.setHavePseudoSection(true);
                }
            }
        }
    }

    private static class MatchRule {
        private final State section;
        private final List<String> variables;
        private final String push;
        private final State gotoState;
        private final Pattern pattern;
        private final DigResult.FailedType failedType;

        private MatchRule(State section, String pattern, List<String> variables, String push, State gotoState, DigResult.FailedType failedType) {
            this.section = section;
            this.variables = variables;
            this.push = push;
            this.gotoState = gotoState;
            this.pattern = Pattern.compile(pattern);
            this.failedType = failedType;
        }

        private MatchRule(State section, String pattern, List<String> variables) {
            this(section, pattern, variables, null, null, DigResult.FailedType.SUCCESS);
        }

        private MatchRule(String pattern, State gotoState) {
            this(null, pattern, null, null, gotoState, DigResult.FailedType.SUCCESS);
        }

        private MatchRule(State section, String pattern, String push) {
            this(section, pattern, null, push, null, DigResult.FailedType.SUCCESS);
        }

        private MatchedData match(DigResultWrapper digResultWrapper, String input) {

            if (this.section == null || this.section == digResultWrapper.currentState) {
                Matcher matcher = pattern.matcher(input);

                if (matcher.matches()) {
                    return new MatchedData(input, this, matcher);
                }
            }
            return null;
        }
    }

    private static final List<MatchRule> RULESET = List.of(
            new MatchRule(
                    State.HEADER_SECTION,
                    ";;\\s*Connection to.*for.*failed:\\s*(.*)",
                    List.of("failedMessage"),
                    null,
                    null,
                    DigResult.FailedType.CONNECTION_FAILED
            ),
            new MatchRule(
                    State.HEADER_SECTION,
                    ";;\\s*connection\\s+(.*); no servers could be reached.*",
                    List.of("failedMessage"),
                    null,
                    null,
                    DigResult.FailedType.CONNECTION_TIMEOUT
            ),
            new MatchRule(
                    State.HEADER_SECTION,
                    ";\\s*Transfer failed\\..*",
                    null,
                    null,
                    null,
                    DigResult.FailedType.TRANSFER_FAILED
            ),
            new MatchRule(
                    State.HEADER_SECTION,
                    ";;.*opcode:\\s*(.*),\\s*status:\\s*(.*),.*",
                    List.of("opcode", "status")
            ),
            new MatchRule(
                    State.HEADER_SECTION,
                    ";;.*flags:\\s*(.*);\\s*QUERY:\\s*(\\d+),\\s*ANSWER:\\s*(\\d+),\\s*AUTHORITY:\\s*(\\d+),\\s*ADDITIONAL:\\s*(\\d+)\\s*$",
                    List.of("tempFlags", "query:int", "answer:int", "authority:int", "additional:int")
            ),
            new MatchRule(
                    State.HEADER_SECTION,
                    ResourceRecord.RESOURCE_RECORD_PATTERN,
                    "axfrRecords"   // push to list
            ),
            new MatchRule(
                    ";;\\s*OPT PSEUDOSECTION:.*",
                    State.OPT_PSEUDOSECTION
            ),
            new MatchRule(
                    State.OPT_PSEUDOSECTION,
                    ";\\s*EDNS:\\s*version:\\s*(\\d+),\\s*flags:\\s*(.*);\\s*udp:\\s*(\\d+).*",
                    List.of("version:int", "ednsTempFlags", "udp:int")
            ),
            new MatchRule(
                    State.OPT_PSEUDOSECTION,
                    ";\\s*OPT=(.*)",
                    List.of("opt")
            ),
            new MatchRule(
                    State.OPT_PSEUDOSECTION,
                    ";\\s*COOKIE:.*\\((\\w+)\\).*",
                    List.of("cookie")
            ),
            new MatchRule(
                    State.OPT_PSEUDOSECTION,
                    ";\\s*CLIENT-SUBNET:\\s*(.*)",
                    List.of("subnet")
            ),
            new MatchRule(
                    ";;\\s*ANSWER SECTION:\\s*",
                    State.ANSWER_SECTION
            ),
            new MatchRule(
                    State.ANSWER_SECTION,
                    ResourceRecord.RESOURCE_RECORD_PATTERN,
                    "answerRecords"
            ),
            new MatchRule(
                    ";;\\s*AUTHORITY SECTION:\\s*",
                    State.AUTHORITY_SECTION
            ),
            new MatchRule(
                    State.AUTHORITY_SECTION,
                    ResourceRecord.RESOURCE_RECORD_PATTERN,
                    "authorityRecords"   // push to list
            ),
            new MatchRule(
                    ";;\\s*ADDITIONAL SECTION:\\s*",
                    State.ADDITIONAL_SECTION
            ),
            new MatchRule(
                    State.ADDITIONAL_SECTION,
                    ResourceRecord.RESOURCE_RECORD_PATTERN,
                    "additionalRecords"   // push to list
            ),
            new MatchRule(
                    null,
                    ";;\\s*MSG SIZE\\s*rcvd:\\s*(\\d+)\\s*",
                    List.of("messageSize:int")
            ),
            new MatchRule(
                    null,
                    ";;\\s*XFR size:\\s*(\\d+)\\s*records\\s*\\(messages\\s*(\\d+),.*",
                    List.of("transferRecords:int", "transferMessages:int")
            )
    );

    public DigServiceImpl(ReactiveCommandExecutor reactiveCommandExecutor) {
        this.reactiveCommandExecutor = reactiveCommandExecutor;
    }

    private DigResult parser(final List<String> data) {
        final DigResultWrapper digResultWrapper = new DigResultWrapper();

        for (String datum : data) {
            RULESET.stream()
                    .map(r -> r.match(digResultWrapper, datum))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .ifPresent(matchedData -> matchedData.handle(digResultWrapper));
        }

        digResultWrapper.digResult.postConstruct();

//        StackTraceUtil.print1(digResultWrapper.digResult);
        return digResultWrapper.digResult;
    }

    @Override
    public Mono<DigResult> dig(final String[] cmd) {
        Instant start = Instant.now();

        return reactiveCommandExecutor.exec(cmd)
                .collectList()
                .map(this::parser)
                .doOnNext(digResult -> {
                    digResult.setElapsedTime(Duration.between(start, Instant.now()).toMillis());
                    logger.info("Elapsed time: {}, Command: {}",
                            digResult.getElapsedTime(),
                            String.join(" ", cmd));
                });
    }
}
