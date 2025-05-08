package br.com.vinimockgen.domain.generators;

import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;

@Singleton
public class FloatValueGenerator implements IValueGenerator<Float> {

    @Override
    public Float generateValue(MockGenConfiguration configuration) {
        return (float) ValueGeneratorUtils.generateDouble(0.0, 123.0);
    }

    @Override
    public String generateStringifiedValue(MockGenConfiguration configuration) {
        final var value = this.generateValue(configuration);
        return String.format("%.4ff", value);
    }

}
