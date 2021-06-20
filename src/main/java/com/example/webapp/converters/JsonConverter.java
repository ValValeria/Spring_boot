package com.example.webapp.converters;

import com.example.webapp.components.ObjectApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter implements Converter<ObjectApiResponse, String> {
    @Override
    public String convert(ObjectApiResponse source) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.writeValueAsString(source);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return "{}";
    }
}
