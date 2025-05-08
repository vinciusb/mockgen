package br.com.vinimockgen.domain.exception;

public class ValueGeneratorNotMappedException extends RuntimeException {

    public ValueGeneratorNotMappedException(Class<?> clazz) {
        super(createMessage(clazz));
    }

    private static String createMessage(Class<?> clazz) {
        return String.format("Could not find any value generator mapped for the following class: %s", clazz.getName());
    }

}
