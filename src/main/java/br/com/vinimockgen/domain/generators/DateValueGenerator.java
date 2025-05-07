package br.com.vinimockgen.domain.generators;

import java.time.Instant;
import java.util.Date;
import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class DateValueGenerator implements IValueGenerator<Date> {

    private final IValueGenerator<Instant> instanValueGenerator;

    @Override
    public Date generateValue(MockGenConfiguration configuration) {
        var instant = instanValueGenerator.generateValue(configuration);
        return Date.from(instant);
    }

    @Override
    public String generateStringifiedValue(MockGenConfiguration configuration) {
        final var value = instanValueGenerator.generateValue(configuration);
        return String.format("Date.from(%s)", value);
    }

}
