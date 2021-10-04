package com.vnpay.redis.serializer;

import com.vnpay.redis.api.Serializer;
import com.vnpay.redis.exeception.SerializationException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by SonCD on 27/09/2021
 */
public class CacheValueSerializer implements Serializer<byte[]> {
    @Override
    public void serialize(byte[] value, OutputStream output) throws SerializationException, IOException {
        output.write(value);
    }
}
