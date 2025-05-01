package br.com.vinimockgen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import br.com.vinimockgen.application.MockGenerator;
import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import io.micronaut.context.ApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        try (ApplicationContext context = ApplicationContext.run()) {
            context.registerSingleton(MockGenConfiguration.class, new MockGenConfiguration(3, 4));
            context.getBean(MockGenConfiguration.class);
            final var mockGenerator = context.getBean(MockGenerator.class);

            final var clazz = Main.loadClass(args[0], args[1]);
            mockGenerator.generateMock(clazz);
        }
    }

    private static Class<?> loadClass(String path, String className) throws ClassNotFoundException, IOException {
        final var root = new File(path);
        final var url = root.toURI().toURL();

        try (URLClassLoader classLoader = new URLClassLoader(new URL[] { url })) {
            return classLoader.loadClass(className);
        }
    }
}