package com.vnpay.redis.standard;

import com.vnpay.redis.api.PropertyValue;
import com.vnpay.redis.util.FormatUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by SonCD on 22/09/2021
 */
public class StandardPropertyValue implements PropertyValue {

    private final String rawValue;

    public StandardPropertyValue(String rawValue) {
        this.rawValue = rawValue;
    }

    @Override
    public String getValue() {
        return rawValue;
    }

    @Override
    public Integer asInteger() {
        return (rawValue == null) ? null : Integer.parseInt(rawValue.trim());
    }

    @Override
    public Long asLong() {
        return (rawValue == null) ? null : Long.parseLong(rawValue.trim());
    }

    @Override
    public Float asFloat() {
        return (rawValue == null) ? null : Float.parseFloat(rawValue.trim());
    }

    @Override
    public Double asDouble() {
        return (rawValue == null) ? null : Double.parseDouble(rawValue.trim());
    }

    @Override
    public boolean isSet() {
        return rawValue != null;
    }

    @Override
    public boolean asBoolean() {
        return (rawValue == null) ? null : Boolean.parseBoolean(rawValue.trim());
    }

    @Override
    public Long asTimePeriod(TimeUnit timeUnit) {
        return (rawValue == null) ? null : FormatUtils.getTimeDuration(rawValue.trim(), timeUnit);
    }
}
