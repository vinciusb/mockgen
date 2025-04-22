package br.com.vinimockgen.domain;

import br.com.vinimockgen.presentation.config.MockGenConfiguration;

public interface IValueGenerator<T> {
    T generateValue(MockGenConfiguration configuration);
}
