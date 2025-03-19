package com.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateToLongConverter implements Converter<LocalDate, Long> {
    @Override
    public Long convert(LocalDate source) {
        return source.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
