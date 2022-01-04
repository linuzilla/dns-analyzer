package ncu.cc.digger.services;

import reactor.core.publisher.Flux;

import java.util.Map;

public interface ReactiveCommandExecutor {
    Map<Long,ExecutionState> dumpProcesses();

    Flux<String> exec(String... cmd);

    class ExecutionState {
        private final String command;
        private String at;

        ExecutionState(String command) {
            this.command = command;
            this.at = "init";
        }

        public String getCommand() {
            return command;
        }

        public String getAt() {
            return at;
        }

        public void setAt(String at) {
            this.at = at;
        }
    }
}
