package com.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class LongToLocalDateConverter implements Converter<Long, LocalDate> {
    @Override
    public LocalDate convert(Long source) {
        return Instant.ofEpochMilli(source)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}