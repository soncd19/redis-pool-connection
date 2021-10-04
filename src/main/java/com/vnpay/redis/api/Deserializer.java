package com.vnpay.redis.api;

import com.vnpay.redis.exeception.DeserializationException;

import java.io.IOException;

/**
 * Created by SonCD on 23/09/2021
 */
public interface Deserializer<T> {
    T deserialize(byte[] input) throws DeserializationException, IOException;
}
