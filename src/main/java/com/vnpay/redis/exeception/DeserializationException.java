package com.vnpay.redis.exeception;

/**
 * Created by SonCD on 23/09/2021
 */
public class DeserializationException extends RuntimeException {
    public DeserializationException(final Throwable cause) {
        super(cause);
    }

    public DeserializationException(final String message) {
        super(message);
    }

    public DeserializationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
