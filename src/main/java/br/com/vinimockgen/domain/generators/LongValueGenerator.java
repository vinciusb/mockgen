package br.com.vinimockgen.domain.generators;

import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;

@Singleton
public class LongValueGenerator implements IValueGenerator<Long> {

    @Override
    public Long generateValue(MockGenConfiguration configuration) {
        final var max = configuration.getIterableMaxSize();
        final var min = configuration.getIterableMinSize();
        return ValueGeneratorUtils.generateLong(min, max);
    }

    @Override
    public String generateStringifiedValue(MockGenConfiguration configuration) {
        final var value = this.generateValue(configuration);
        return String.valueOf(String.format("%dL", value));
    }

}
