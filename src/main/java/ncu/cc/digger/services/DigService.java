package ncu.cc.digger.services;

import ncu.cc.digger.models.DigResult;
import reactor.core.publisher.Mono;

public interface DigService {
    Mono<DigResult> dig(String[] cmd);

    default Mono<DigResult> digWithArgs(String ...commandAndArgs) {
        return dig(commandAndArgs);
    }

    default Mono<DigResult> digWithShell(String command) {
        return dig(command.split("\\s+"));
    }
}
