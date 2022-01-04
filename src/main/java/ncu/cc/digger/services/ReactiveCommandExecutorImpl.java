package ncu.cc.digger.services;

import ncu.cc.commons.utils.StackTraceUtil;
import ncu.cc.digger.constants.BeanIds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReactiveCommandExecutorImpl implements ReactiveCommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ReactiveCommandExecutorImpl.class);

    private final Scheduler scheduler;
    private final AtomicInteger numberOfProcess = new AtomicInteger(0);
    private final AtomicLong serialNo = new AtomicLong(0L);
    private final Map<Long, ExecutionState> processMap = new ConcurrentHashMap<>();

    public ReactiveCommandExecutorImpl(@Qualifier(BeanIds.SCHEDULER) Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public Map<Long, ExecutionState> dumpProcesses() {
        StackTraceUtil.print1(processMap);
        return processMap;
    }

    @Override
    public Flux<String> exec(final String... cmd) {
        EmitterProcessor<String> stream = EmitterProcessor.create();

        scheduler.schedule(() -> {
            numberOfProcess.incrementAndGet();
            Long sn = serialNo.incrementAndGet();
            ExecutionState executionState = new ExecutionState(String.join(" ", cmd));
            processMap.put(sn, executionState);

            try {
                ProcessBuilder processBuilder = new ProcessBuilder();

//                logger.info("execute: [ {} ]", String.join(" ", cmd));

                processBuilder.command(cmd);
                Process process = processBuilder.start();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                String line;
                int lineNumber = 0;

                while ((line = reader.readLine()) != null) {
                    executionState.setAt(String.format("line: %d, [ %s ]", ++lineNumber, line));
                    stream.onNext(line);
                    executionState.setAt("after stream.onNext()");
                }

                executionState.setAt("exit readLine() loop");
                process.waitFor();
                executionState.setAt("finish waitFor()");
            } catch (Exception e) {
                logger.error("exception: {}", e.getMessage());
            } finally {
                try {
                    executionState.setAt("before onComplete()");
                    stream.onComplete();
                    executionState.setAt("after onComplete()");
                } catch (Exception e) {
                    logger.error("exception: {}", e.getMessage());
                }
            }
            logger.trace("current number of process: {}", numberOfProcess.decrementAndGet());
            processMap.remove(sn);
        });

        return stream;
    }
}
