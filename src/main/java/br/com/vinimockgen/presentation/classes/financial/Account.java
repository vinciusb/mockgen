package br.com.vinimockgen.presentation.classes.financial;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
    private String accountNumber;
    private String accountType;
    private String currency;
    private Boolean isClosed;
    private LocalDateTime openedAt;
    private List<Transaction> transactions;
}