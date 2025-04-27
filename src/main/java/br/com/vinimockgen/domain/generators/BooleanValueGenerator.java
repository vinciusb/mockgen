package br.com.vinimockgen.domain.generators;

import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;

@Singleton
public class BooleanValueGenerator implements IValueGenerator<Boolean> {

    @Override
    public Boolean generateValue(MockGenConfiguration configuration) {
        final var intValue = (int) (Math.random() * Integer.MAX_VALUE);
        return intValue % 2 == 0;
    }

    @Override
    public String generateStringifiedValue(MockGenConfiguration configuration) {
        final var value = this.generateValue(configuration);
        return value.toString();
    }

}
