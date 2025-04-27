package br.com.vinimockgen.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import br.com.vinimockgen.domain.BuildPolicy;
import br.com.vinimockgen.domain.Variable;
import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class Parser {

    private List<String> codeLines;
    private Map<String, Integer> occurencesByName;

    private final MockGenConfiguration config;

    public String parse(Variable rootVar) {
        codeLines = new ArrayList<>();
        occurencesByName = new HashMap<>();

        var rootClassName = rootVar.getClazz().getClazz().getSimpleName().toLowerCase();
        _parse(rootVar, rootClassName);

        return codeLines.stream().collect(Collectors.joining(System.lineSeparator()));
    }

    private String _parse(Variable rootVar, String propertyName) {
        try {
            final var rootClass = rootVar.getClazz();
            final var varName = buildVariableName(propertyName);

            if (rootClass.isPseudoPrimitive()) {
                final var varValue = rootVar.getValueGenerator().generateStringifiedValue(config);
                declareVariable(varName, varValue);
                return varName;
            }

            final var fields = new ArrayList<>(rootClass.getFields().keySet());

            final var builderVars = IntStream.range(0, rootVar.getBuilderVariables().size())
                    .mapToObj(idx -> {
                        if (rootClass.isIterable()) {
                            return this._parse(rootVar.getBuilderVariables().get(idx), fields.get(0));
                        }
                        return this._parse(rootVar.getBuilderVariables().get(idx), fields.get(idx));
                    })
                    .toList();

            buildComplexClass(rootVar, varName, builderVars);

            return varName;
        } catch (Exception ex) {
            log.error(String.format(
                    "Error while trying to parse the Hierarchy Tree. Error happend for the \n{\n\t\"variable\": \"%s\"}\n",
                    rootVar),
                    ex);
            throw ex;
        }
    }

    private String buildVariableName(String propertyName) {
        var numOcurrences = occurencesByName.getOrDefault(propertyName, -1);
        occurencesByName.put(propertyName, numOcurrences + 1);

        if (numOcurrences == -1)
            return propertyName;

        return String.format("%s%d", propertyName, numOcurrences + 1);
    }

    private void declareVariable(String varName, String varValue) {
        codeLines.add(String.format("var %s = %s;", varName, varValue));
    }

    private void buildComplexClass(Variable rootVar, String propertyName, List<String> builderVars) {
        final var rootClass = rootVar.getClazz();

        if (rootVar.getClazz().getBuildPolicy() == BuildPolicy.BUILDER) {
            final var fieldNames = new ArrayList<>(rootClass.getFields().keySet());
            final var builderLines = IntStream
                    .range(0, fieldNames.size())
                    .mapToObj((idx) -> String.format("%s(%s)", fieldNames.get(idx), builderVars.get(idx)))
                    .collect(Collectors.joining("."));

            codeLines.add(String.format("var %s = %s.builder().%s.build();",
                    propertyName,
                    rootClass.getClazz().getSimpleName(),
                    builderLines));
        } else {
            final var argsString = builderVars.stream().collect(Collectors.joining(", "));
            codeLines.add(
                    String.format("var %s = new %s(%s);",
                            propertyName,
                            rootClass.getClazz().getName(),
                            argsString));
        }
    }
}
