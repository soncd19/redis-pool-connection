package com.vnpay.redis.serializer;

import com.vnpay.redis.api.Deserializer;
import com.vnpay.redis.exeception.DeserializationException;

import java.io.IOException;

/**
 * Created by SonCD on 27/09/2021
 */
public class CacheValueDeserializer implements Deserializer<byte[]> {
    @Override
    public byte[] deserialize(byte[] input) throws DeserializationException, IOException {
        if (input == null || input.length == 0) {
            return null;
        }
        return input;
    }
}
