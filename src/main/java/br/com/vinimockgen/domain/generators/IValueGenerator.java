package br.com.vinimockgen.domain.generators;

import br.com.vinimockgen.presentation.config.MockGenConfiguration;

public interface IValueGenerator<T> {
    T generateValue(MockGenConfiguration configuration);

    String generateStringifiedValue(MockGenConfiguration configuration);
}
