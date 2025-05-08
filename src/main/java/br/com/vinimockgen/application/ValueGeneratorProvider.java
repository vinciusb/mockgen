package br.com.vinimockgen.application;

import br.com.vinimockgen.domain.generators.IValueGenerator;
import br.com.vinimockgen.domain.generators.InstantValueGenerator;
import br.com.vinimockgen.domain.generators.IntValueGenerator;
import br.com.vinimockgen.domain.generators.LocalDateTimeValueGenerator;
import br.com.vinimockgen.domain.generators.LocalDateValueGenerator;
import br.com.vinimockgen.domain.generators.LongValueGenerator;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import br.com.vinimockgen.domain.exception.ValueGeneratorNotMappedException;
import br.com.vinimockgen.domain.generators.BooleanValueGenerator;
import br.com.vinimockgen.domain.generators.DateValueGenerator;
import br.com.vinimockgen.domain.generators.DoubleValueGenerator;
import br.com.vinimockgen.domain.generators.FloatValueGenerator;
import br.com.vinimockgen.domain.generators.StringValueGenerator;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class ValueGeneratorProvider {
    public IValueGenerator getByClass(Class clazz) {
        if (clazz.equals(Integer.class))
            return intValueGenerator;
        if (clazz.equals(String.class))
            return stringValueGenerator;
        if (clazz.equals(Double.class))
            return doubleValueGenerator;
        if (clazz.equals(Float.class))
            return floatValueGenerator;
        if (clazz.equals(Long.class))
            return longValueGenerator;
        if (clazz.equals(Boolean.class))
            return booleanValueGenerator;
        if (clazz.equals(LocalDateTime.class))
            return localDateTimeValueGenerator;
        if (clazz.equals(LocalDate.class))
            return localDateValueGenerator;
        if (clazz.equals(Date.class))
            return dateValueGenerator;
        if (clazz.equals(Instant.class))
            return instantValueGenerator;

        throw new ValueGeneratorNotMappedException(clazz);
    }

    private final IntValueGenerator intValueGenerator;
    private final StringValueGenerator stringValueGenerator;
    private final DoubleValueGenerator doubleValueGenerator;
    private final FloatValueGenerator floatValueGenerator;
    private final LongValueGenerator longValueGenerator;
    private final BooleanValueGenerator booleanValueGenerator;
    private final LocalDateTimeValueGenerator localDateTimeValueGenerator;
    private final LocalDateValueGenerator localDateValueGenerator;
    private final DateValueGenerator dateValueGenerator;
    private final InstantValueGenerator instantValueGenerator;
}
