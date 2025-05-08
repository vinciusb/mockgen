package br.com.vinimockgen.presentation.classes.numeric;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RootNumeric {
    private Integer integer;
    private Double _double;
    private Long _long;
    private Float _float;
}