package br.com.vinimockgen.application;

import br.com.vinimockgen.domain.generators.IValueGenerator;
import br.com.vinimockgen.domain.generators.IntValueGenerator;
import br.com.vinimockgen.domain.generators.BooleanValueGenerator;
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
        if (clazz.equals(Boolean.class))
            return booleanValueGenerator;

        throw new RuntimeException(); // TODO: Add own exception
    }

    private final IntValueGenerator intValueGenerator;
    private final StringValueGenerator stringValueGenerator;
    private final BooleanValueGenerator booleanValueGenerator;
}
