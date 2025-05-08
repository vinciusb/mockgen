package br.com.vinimockgen.domain.generators;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValueGeneratorUtils {
    public static int generateInt(int min, int max) {
        final var value = (int) (Math.random() * (max - min) + min);
        return value;
    }

    public static long generateLong(long min, long max) {
        final var value = (long) (Math.random() * (max - min) + min);
        return value;
    }

    public static double generateDouble(double min, double max) {
        final var value = (Math.random() * (max - min) + min);
        return value;
    }
}
