package com.vnpay.redis.serializer;

import com.vnpay.redis.api.Serializer;
import com.vnpay.redis.exeception.SerializationException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by SonCD on 27/09/2021
 */
public class StringSerializer implements Serializer<String> {
    @Override
    public void serialize(String value, OutputStream output) throws SerializationException, IOException {
        output.write(value.getBytes(StandardCharsets.UTF_8));
    }
}
