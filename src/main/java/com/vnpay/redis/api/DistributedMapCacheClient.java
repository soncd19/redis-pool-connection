package com.vnpay.redis.api;


import java.io.IOException;
import java.util.List;

/**
 * Created by SonCD on 23/09/2021
 */
public interface DistributedMapCacheClient {

    <K, V> boolean putIfAbsent(K key, V value, Serializer<K> keySerializer, Serializer<V> valueSerializer) throws IOException;

    <K, V> V getAndPutIfAbsent(K key, V value, Serializer<K> keySerializer, Serializer<V> valueSerializer, Deserializer<V> valueDeserializer) throws IOException;

    <K> boolean containsKey(K key, Serializer<K> keySerializer) throws IOException;

    <K, V> void put(K key, V value, Serializer<K> keySerializer, Serializer<V> valueSerializer) throws IOException;

    <K, V> void set(K key, V value, Long exp, Serializer<K> keySerializer, Serializer<V> valueSerializer) throws IOException;

    <K, V> V get(K key, Serializer<K> keySerializer, Deserializer<V> valueDeserializer) throws IOException;

    <K, V> List<V> getList(K key, Serializer<K> keySerializer, Deserializer<V> valueDeserializer) throws IOException;

    <K> boolean remove(K key, Serializer<K> serializer) throws IOException;

    long removeByPattern(String regex) throws IOException;


}
