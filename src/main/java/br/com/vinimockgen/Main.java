package br.com.vinimockgen;

import br.com.vinimockgen.application.MockGenerator;
import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import io.micronaut.context.ApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        try (ApplicationContext context = ApplicationContext.run()) {
            context.registerSingleton(MockGenConfiguration.class, new MockGenConfiguration());
            var mockGenConfiguration = context.getBean(MockGenConfiguration.class);
            final var mockGenerator = context.getBean(MockGenerator.class);
            mockGenerator.generateMock();
        }
    }
}