package kfoodbox.common.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisClient {
    private final ValueOperations<String, String> valueOperations;

    private final String SIGNUP_AUTHENTICATION_NUMBER_PREFIX = "/signup/authentication-number";
    private final long SIGNUP_AUTHENTICATION_NUMBER_TIMEOUT_MINUTE = 5;

    public RedisClient(RedisTemplate<String, String> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    public void putSignupAuthenticationNumber(String email, String authenticationNumber) {
        String key = String.format("%s/%s", SIGNUP_AUTHENTICATION_NUMBER_PREFIX, email);
        valueOperations.set(key, authenticationNumber, Duration.ofMinutes(SIGNUP_AUTHENTICATION_NUMBER_TIMEOUT_MINUTE));
    }
}
