package com.vnpay.redis.api;

import com.vnpay.redis.util.PropertyDescriptor;

import java.util.Map;

/**
 * Created by SonCD on 22/09/2021
 */
public interface PropertyContext {

    PropertyValue getProperty(PropertyDescriptor descriptor);

    Map<PropertyDescriptor, String> getProperties();

    Map<String, String> getAllProperties();
}
