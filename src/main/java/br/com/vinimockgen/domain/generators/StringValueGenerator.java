package br.com.vinimockgen.domain.generators;

import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;

@Singleton
public class StringValueGenerator implements IValueGenerator<String> {

    @Override
    public String generateValue(MockGenConfiguration configuration) {
        final var a = Math.random();
        return "\"" + String.valueOf(a) + "\"";
    }

    @Override
    public String generateStringifiedValue(MockGenConfiguration configuration) {
        return this.generateValue(configuration);
    }

}
