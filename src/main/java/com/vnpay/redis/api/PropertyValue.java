package com.vnpay.redis.api;

import java.util.concurrent.TimeUnit;

/**
 * Created by SonCD on 22/09/2021
 */
public interface PropertyValue {

    String getValue();

    Integer asInteger();

    Long asLong();

    Float asFloat();

    Double asDouble();

    boolean isSet();

    boolean asBoolean();

    Long asTimePeriod(TimeUnit timeUnit);
}
