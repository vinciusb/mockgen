package br.com.vinimockgen.domain.generators;

import java.time.LocalDate;
import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class LocalDateValueGenerator implements IValueGenerator<LocalDate> {

    @Override
    public LocalDate generateValue(MockGenConfiguration configuration) {
        int year = ValueGeneratorUtils.generateInt(2001, 2025);
        int month = ValueGeneratorUtils.generateInt(1, 12);
        int day = ValueGeneratorUtils.generateInt(1, 28);
        return LocalDate.of(year, month, day);
    }

    @Override
    public String generateStringifiedValue(MockGenConfiguration configuration) {
        final var value = this.generateValue(configuration);
        final var argsStr = String.format(
                "%d,%d,%d",
                value.getYear(),
                value.getMonthValue(),
                value.getDayOfMonth());
        return String.format("LocalDate.of(%s)", argsStr);
    }

}
