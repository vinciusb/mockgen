package br.com.vinimockgen.domain.generators;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValueGeneratorUtils {
    public static int generateInt(int min, int max) {
        final var value = (int) (Math.random() * (max - min) + min);
        return value;
    }
}
