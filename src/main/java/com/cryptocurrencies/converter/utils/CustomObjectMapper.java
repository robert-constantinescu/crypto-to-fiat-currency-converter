package com.cryptocurrencies.converter.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import static com.cryptocurrencies.converter.utils.Constants.dateFormatWithMillis;

@Component
public class CustomObjectMapper {

    private final ObjectMapper objectMapper;

    public CustomObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        objectMapper.setDateFormat(dateFormatWithMillis);
    }

    public ObjectMapper getCustomMapper() {
        return objectMapper;
    }
}
