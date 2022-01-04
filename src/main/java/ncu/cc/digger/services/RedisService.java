package ncu.cc.digger.services;

import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public interface RedisService {
    void store(String key, String value, int seconds);

//    String retrieve(String key);
//
//    String retrieveAndRemove(String key);

    Mono<Boolean> asyncStore(String key, String value, int seconds);

    Mono<Optional<String>> asyncRetrieve(String key);

    Mono<Long> asyncRemove(String key);

    Mono<String> asyncRetrieveAndRemove(String key);
}
