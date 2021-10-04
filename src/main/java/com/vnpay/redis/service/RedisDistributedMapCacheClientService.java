package com.vnpay.redis.service;

import com.vnpay.redis.api.*;
import com.vnpay.redis.standard.StandardProcessContext;
import com.vnpay.redis.util.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by SonCD on 22/09/2021
 */
public class RedisDistributedMapCacheClientService implements DistributedMapCacheClient {
    private static final Logger logger = LoggerFactory.getLogger(RedisDistributedMapCacheClientService.class);
    private volatile RedisConnectionPool redisConnectionPool;

    public RedisDistributedMapCacheClientService(String configPath) throws Exception {
        initContext(configPath);
    }

    private void initContext(String configPath) throws Exception {
        PropertyContext propertyContext = new StandardProcessContext(configPath);
        onEnableRedisPool(propertyContext);
    }

    private void onEnableRedisPool(PropertyContext propertyContext) {
        this.redisConnectionPool = new RedisConnectionPoolService(propertyContext);
        this.redisConnectionPool.onEnable();
    }

    public void disable() {
        redisConnectionPool.onDisable();
    }

    @Override
    public <K, V> boolean putIfAbsent(K key, V value, Serializer<K> keySerializer, Serializer<V> valueSerializer) throws IOException {

        return false;
    }

    @Override
    public <K, V> V getAndPutIfAbsent(K key, V value, Serializer<K> keySerializer, Serializer<V> valueSerializer, Deserializer<V> valueDeserializer) throws IOException {
        return null;
    }

    @Override
    public <K> boolean containsKey(K key, Serializer<K> keySerializer) throws IOException {
        return withConnection(redisConnection -> {
            final byte[] k = serialize(key, keySerializer);
            return redisConnection.exists(k);
        });
    }

    @Override
    public <K, V> void put(K key, V value, Serializer<K> keySerializer, Serializer<V> valueSerializer) throws IOException {
        withConnection(redisConnection -> {
            final Tuple<byte[], byte[]> kv = serialize(key, value, keySerializer, valueSerializer);
            redisConnection.rPush(kv.getKey(), kv.getValue());
            return null;
        });
    }

    @Override
    public <K, V> void set(K key, V value, Long exp, Serializer<K> keySerializer, Serializer<V> valueSerializer) throws IOException {
        withConnection(redisConnection -> {
            final Tuple<byte[], byte[]> kv = serialize(key, value, keySerializer, valueSerializer);
            redisConnection.set(kv.getKey(), kv.getValue(), Expiration.seconds(exp), RedisStringCommands.SetOption.upsert());
            return null;
        });
    }

    @Override
    public <K, V> V get(K key, Serializer<K> keySerializer, Deserializer<V> valueDeserializer) throws IOException {
        return withConnection(redisConnection -> {
            final byte[] k = serialize(key, keySerializer);
            final byte[] v = redisConnection.get(k);
            return valueDeserializer.deserialize(v);
        });
    }

    @Override
    public <K, V> List<V> getList(K key, Serializer<K> keySerializer, Deserializer<V> valueDeserializer) throws IOException {
        return withConnection(redisConnection -> {
            final byte[] k = serialize(key, keySerializer);
            List<byte[]> v = redisConnection.lRange(k, 0, -1);
            return null;
        });
    }

    @Override
    public <K> boolean remove(K key, Serializer<K> serializer) throws IOException {
        return withConnection(redisConnection -> {
            final byte[] k = serialize(key, serializer);
            final long nRemoved = redisConnection.del(k);
            return nRemoved > 0;
        });
    }

    @Override
    public long removeByPattern(String regex) throws IOException {
        return 0;
    }

    private <K, V> Tuple<byte[], byte[]> serialize(final K key, final V value, final Serializer<K> keySerializer, final Serializer<V> valueSerializer) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        keySerializer.serialize(key, out);
        final byte[] k = out.toByteArray();

        out.reset();

        valueSerializer.serialize(value, out);
        final byte[] v = out.toByteArray();

        return new Tuple<>(k, v);
    }

    private <K> byte[] serialize(final K key, final Serializer<K> keySerializer) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        keySerializer.serialize(key, out);
        return out.toByteArray();
    }

    private <T> T withConnection(final RedisAction<T> action) throws IOException {
        RedisConnection redisConnection = null;
        try {
            redisConnection = redisConnectionPool.getConnection();
            return action.execute(redisConnection);
        } finally {
            if (redisConnection != null) {
                try {
                    redisConnection.close();
                } catch (Exception e) {
                    logger.warn("Error closing connection: " + e.getMessage(), e);
                }
            }
        }
    }
}
