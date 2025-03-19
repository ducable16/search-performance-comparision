package com.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class IntegerToLocalDateConverter implements Converter<Integer, LocalDate> {
    @Override
    public LocalDate convert(Integer source) {
        return Instant.ofEpochMilli(source)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Override
    public <U> Converter<Integer, U> andThen(Converter<? super LocalDate, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}