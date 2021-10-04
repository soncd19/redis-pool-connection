package com.vnpay.redis.util;

import com.vnpay.redis.api.Deserializer;
import com.vnpay.redis.api.DistributedMapCacheClient;
import com.vnpay.redis.api.Serializer;
import com.vnpay.redis.serializer.CacheValueDeserializer;
import com.vnpay.redis.serializer.CacheValueSerializer;
import com.vnpay.redis.serializer.StringSerializer;
import com.vnpay.redis.service.RedisDistributedMapCacheClientService;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by SonCD on 27/09/2021
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String redisPath = System.getProperty("user.dir") + "/src/main/resources/redis-config.xml";
        Serializer<String> keySerializer = new StringSerializer();
        Serializer<byte[]> valueSerializer = new CacheValueSerializer();
        Deserializer<byte[]> valueDeserializer = new CacheValueDeserializer();
        DistributedMapCacheClient distributedMapCacheClient = new RedisDistributedMapCacheClientService(redisPath);
        String key = "soncdtestnewpool";
        String value = "cao dinh son1";
        //distributedMapCacheClient.put(key, value.getBytes(StandardCharsets.UTF_8), keySerializer, valueSerializer);
        distributedMapCacheClient.set(key, value.getBytes(StandardCharsets.UTF_8), 60L, keySerializer, valueSerializer);
        byte[] values = distributedMapCacheClient.get(key, keySerializer, valueDeserializer);


        List<byte[]> valuesList = distributedMapCacheClient.getList(key, keySerializer, valueDeserializer);

        String valueString = new String(values);
        System.out.println(valueString);

    }
}
