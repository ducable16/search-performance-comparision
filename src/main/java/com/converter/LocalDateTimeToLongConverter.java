package com.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeToLongConverter implements Converter<LocalDateTime, Long> {
    @Override
    public Long convert(LocalDateTime source) {
        return source.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
