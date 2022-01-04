package ncu.cc.digger.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Service
public class RedisServiceImpl implements RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    private final StringRedisTemplate stringRedisTemplate;
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public RedisServiceImpl(StringRedisTemplate stringRedisTemplate, ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    @Override
    public void store(String key, String value, int seconds) {
        stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Mono<Boolean> asyncStore(String key, String value, int seconds) {
        logger.trace("Store key: '{}' with: {}", key, value);
        return reactiveRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(seconds));
    }

    @Override
    public Mono<Optional<String>> asyncRetrieve(String key) {
        logger.trace("Retrieve key: {}", key);
        return reactiveRedisTemplate.hasKey(key)
                .flatMap(hasKey -> hasKey ? reactiveRedisTemplate.opsForValue().get(key).map(Optional::of)
                        : Mono.just(Optional.empty()));
    }

    @Override
    public Mono<Long> asyncRemove(String key) {
        return reactiveRedisTemplate.delete(key);
    }

    @Override
    public Mono<String> asyncRetrieveAndRemove(final String key) {
        return reactiveRedisTemplate.opsForValue().get(key)
                .flatMap(value -> {
                    if (value != null && value.length() > 0) {
                        return reactiveRedisTemplate.delete(key).flatMap(aLong -> Mono.just(value));
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
