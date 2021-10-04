package com.vnpay.redis.service;

import com.vnpay.redis.api.PropertyContext;
import com.vnpay.redis.api.RedisConnectionPool;
import com.vnpay.redis.api.RedisType;
import com.vnpay.redis.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;


/**
 * Created by SonCD on 22/09/2021
 */
public class RedisConnectionPoolService implements RedisConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(RedisConnectionPoolService.class);
    private PropertyContext propertyContext;
    private RedisType redisType;
    private JedisConnectionFactory connectionFactory;

    public RedisConnectionPoolService(PropertyContext propertyContext) {
        this.propertyContext = propertyContext;
    }

    @Override
    public void onEnable() {
        String redisMode = propertyContext.getProperty(RedisUtils.REDIS_MODE).getValue();
        this.redisType = RedisType.fromDisplayName(redisMode);
    }

    @Override
    public void onDisable() {
        if (connectionFactory != null) {
            connectionFactory.destroy();
            connectionFactory = null;
            redisType = null;
            propertyContext = null;
        }
    }

    @Override
    public RedisConnection getConnection() {
        if (connectionFactory == null) {
            synchronized (this) {
                if (connectionFactory == null) {
                    connectionFactory = RedisUtils.createConnectionFactory(propertyContext, logger);
                }
            }
        }
        return connectionFactory.getConnection();
    }

    @Override
    public RedisType getRedisType() {
        return redisType;
    }
}
