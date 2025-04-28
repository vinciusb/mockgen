package br.com.vinimockgen.domain.generators;

import java.time.LocalDateTime;

import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class LocalDateTimeValueGenerator implements IValueGenerator<LocalDateTime> {

    @Override
    public LocalDateTime generateValue(MockGenConfiguration configuration) {
        int year = ValueGeneratorUtils.generateInt(2001, 2025);
        int month = ValueGeneratorUtils.generateInt(1, 12);
        int day = ValueGeneratorUtils.generateInt(1, 28);
        int hour = ValueGeneratorUtils.generateInt(0, 23);
        int minute = ValueGeneratorUtils.generateInt(0, 59);
        int second = ValueGeneratorUtils.generateInt(0, 59);
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    @Override
    public String generateStringifiedValue(MockGenConfiguration configuration) {
        final var value = this.generateValue(configuration);
        final var argsStr = String.format(
                "%d,%d,%d,%d,%d,%d",
                value.getYear(),
                value.getMonthValue(),
                value.getDayOfMonth(),
                value.getHour(),
                value.getMinute(),
                value.getSecond());
        return String.format("new LocalDateTime(%s)", argsStr);
    }

}
