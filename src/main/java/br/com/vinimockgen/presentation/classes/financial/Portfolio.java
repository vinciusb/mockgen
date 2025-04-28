package br.com.vinimockgen.presentation.classes.financial;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Portfolio {
    private String portfolioId;
    private String ownerName;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private List<Account> accounts;
}