package br.com.vinimockgen;

import br.com.vinimockgen.application.MockGenerator;
import io.micronaut.context.ApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        try (ApplicationContext context = ApplicationContext.run()) {
            final var mockGenerator = context.getBean(MockGenerator.class);
            mockGenerator.generateMock(null);
        }
    }
}