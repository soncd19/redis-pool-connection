package com.vnpay.redis.api;

import org.springframework.data.redis.connection.RedisConnection;

/**
 * Created by SonCD on 22/09/2021
 */
public interface RedisConnectionPool {

    void onEnable();

    void onDisable();

    RedisConnection getConnection();

    RedisType getRedisType();
}
