package br.com.vinimockgen.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Variable {

    private final Clazz clazz;
    private final List<Variable> builderVariables;
    private final IValueGenerator valueGenerator;

}
