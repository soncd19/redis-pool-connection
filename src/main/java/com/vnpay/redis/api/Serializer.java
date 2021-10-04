package com.vnpay.redis.api;

import com.vnpay.redis.exeception.SerializationException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by SonCD on 23/09/2021
 */
public interface Serializer<T> {
    void serialize(T value, OutputStream output) throws SerializationException, IOException;
}
