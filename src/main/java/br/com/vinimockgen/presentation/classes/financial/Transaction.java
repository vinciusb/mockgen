package br.com.vinimockgen.presentation.classes.financial;

import java.time.LocalDateTime;
import lombok.Data;

@Data
class Transaction {
    private String transactionId;
    private String description;
    private Double amount;
    private String category;
    private Boolean isCredit;
    // private LocalDateTime transactionDate;
}