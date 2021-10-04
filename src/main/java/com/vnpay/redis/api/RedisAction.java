package com.vnpay.redis.api;

import org.springframework.data.redis.connection.RedisConnection;

import java.io.IOException;

/**
 * Created by SonCD on 23/09/2021
 */
public interface RedisAction<T> {
    T execute(RedisConnection redisConnection) throws IOException;
}
