package br.com.vinimockgen.domain.generators;

import java.time.Instant;
import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class InstantValueGenerator implements IValueGenerator<Instant> {

    @Override
    public Instant generateValue(MockGenConfiguration configuration) {
        return Instant.now();
    }

    @Override
    public String generateStringifiedValue(MockGenConfiguration configuration) {
        return "Instant.now()";
    }

}
