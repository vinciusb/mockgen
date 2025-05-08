package br.com.vinimockgen.presentation.classes.temporal;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RootTemporal {
    private LocalDateTime localDateTime;
    private LocalDate localDate;
    private Date date;
    private Instant instant;
}