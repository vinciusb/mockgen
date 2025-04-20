package br.com.vinimockgen.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.vinimockgen.domain.BuildPolicy;
import br.com.vinimockgen.domain.Clazz;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class Parser {

    private List<String> codeLines;
    private Map<String, Integer> occurencesByName;

    public String parse(Clazz rootClass) {
        codeLines = new ArrayList<>();
        occurencesByName = new HashMap<>();

        parse(rootClass, rootClass.getClazz().getTypeName().toLowerCase());

        return codeLines.stream().collect(Collectors.joining(System.lineSeparator()));
    }

    private String parse(Clazz rootClass, String propertyName) {
        try {
            if (rootClass.isPseudoPrimitive()) {
                final var varName = buildVariableName(propertyName);
                declareVariable(rootClass, varName);
                return varName;
            }

            var fieldNameToVarName = new HashMap<String, String>();

            for (var entry : rootClass.getFields().entrySet()) {
                final var varName = parse(entry.getValue(), entry.getKey());
                fieldNameToVarName.put(entry.getKey(), varName);
            }

            final var varName = buildVariableName(propertyName);
            buildComplexClass(rootClass, varName, fieldNameToVarName);

            return varName;
        } catch (Exception ex) {
            log.error(String.format(
                    "Error while trying to parse the Hierarchy Tree. Error happend for the \n{\n\t\"propertyName\": \"%s\",\n\t\"class\": \"%s\"}\n",
                    propertyName, rootClass),
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

    private void declareVariable(Clazz clazz, String propertyName) {
        codeLines.add(String.format("var %s = new %s();", propertyName, clazz.getClazz().getName()));
    }

    private void buildComplexClass(Clazz clazz, String propertyName, Map<String, String> fieldNameToVarName) {
        if (clazz.getBuildPolicy() == BuildPolicy.BUILDER) {
            var builderLines = clazz.getFields()
                    .entrySet()
                    .stream()
                    .map(entry -> String.format("%s(%s)", entry.getKey(), fieldNameToVarName.get(entry.getKey())))
                    .collect(Collectors.joining("."));

            codeLines.add(String.format("var %s = %s.builder().%s.build();",
                    clazz.getClass().getSimpleName(),
                    propertyName,
                    builderLines));
        } else {
            codeLines.add(String.format("var %s = new %s();", propertyName, clazz.getClazz().getName()));
        }
    }
}
