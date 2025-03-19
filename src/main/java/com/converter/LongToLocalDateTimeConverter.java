package com.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {
    @Override
    public LocalDateTime convert(Long source) {
        return Instant.ofEpochMilli(source)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}