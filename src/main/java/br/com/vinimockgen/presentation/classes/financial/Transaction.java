package br.com.vinimockgen.presentation.classes.financial;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Transaction {
    private String transactionId;
    private String description;
    // private Double amount;
    private String category;
    private Boolean isCredit;
    private LocalDateTime transactionDate;
}