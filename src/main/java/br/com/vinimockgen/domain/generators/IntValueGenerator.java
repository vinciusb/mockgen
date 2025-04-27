package br.com.vinimockgen.domain.generators;

import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;

@Singleton
public class IntValueGenerator implements IValueGenerator<Integer> {

    @Override
    public Integer generateValue(MockGenConfiguration configuration) {
        final var max = configuration.getIterableMaxSize();
        final var min = configuration.getIterableMinSize();
        final var value = (int) Math.random() * (max - min) + min;
        return value;
    }

    @Override
    public String generateStringifiedValue(MockGenConfiguration configuration) {
        final var value = this.generateValue(configuration);
        return String.valueOf(value);
    }

}
