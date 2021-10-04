package com.vnpay.redis.standard;

import com.vnpay.redis.api.PropertyContext;
import com.vnpay.redis.api.PropertyValue;
import com.vnpay.redis.util.PropertyDescriptor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by SonCD on 22/09/2021
 */
public class StandardProcessContext implements PropertyContext {

    private final Map<PropertyDescriptor, String> properties;

    public StandardProcessContext(String redisConfigPath) throws Exception {
        properties = Collections.unmodifiableMap(StandardParserConfig.parser(redisConfigPath));
    }

    @Override
    public PropertyValue getProperty(PropertyDescriptor descriptor) {
        final String setPropertyValue = properties.get(descriptor);
        if (setPropertyValue != null) {
            return new StandardPropertyValue(setPropertyValue);
        }
        final String defaultValue = descriptor.getDefaultValue();
        return new StandardPropertyValue(defaultValue);
    }

    @Override
    public Map<PropertyDescriptor, String> getProperties() {
        return properties;
    }

    @Override
    public Map<String, String> getAllProperties() {
        final Map<String, String> propValueMap = new LinkedHashMap<>();
        for (final Map.Entry<PropertyDescriptor, String> entry : getProperties().entrySet()) {
            propValueMap.put(entry.getKey().getName(), entry.getValue());
        }
        return propValueMap;
    }
}
