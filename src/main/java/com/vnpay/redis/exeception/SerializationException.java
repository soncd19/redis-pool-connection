package com.vnpay.redis.exeception;

/**
 * Created by SonCD on 23/09/2021
 */
public class SerializationException extends RuntimeException {

    public SerializationException(final Throwable cause) {
        super(cause);
    }

    public SerializationException(final String message) {
        super(message);
    }

    public SerializationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
