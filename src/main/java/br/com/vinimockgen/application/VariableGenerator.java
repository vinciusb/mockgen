package br.com.vinimockgen.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.github.javaparser.utils.Pair;
import br.com.vinimockgen.domain.BuildPolicy;
import br.com.vinimockgen.domain.Clazz;
import br.com.vinimockgen.domain.Variable;
import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class VariableGenerator {

    private final MockGenConfiguration mockGenConfig;
    private final RandomValueGenerator randomValueGenerator;

    public Variable generateVariables(Clazz rootClass) {
        var rootVar = Variable.builder().clazz(rootClass);

        final var fieldToVariableMap = Optional.ofNullable(rootClass.getFields())
                .map(Map<String, Clazz>::entrySet)
                .orElse(Collections.emptySet())
                .stream()
                .map(entry -> new Pair<>(entry.getKey(), generateVariables(entry.getValue())))
                .collect(Collectors.toMap(p -> p.a, p -> p.b));

        rootVar.builderVariables(getOrderedVariablesByBuildPolicy(rootClass, fieldToVariableMap));

        return rootVar.build();
    }

    private List<Variable> getOrderedVariablesByBuildPolicy(Clazz rootClass, Map<String, Variable> fieldToVariableMap) {
        if (rootClass.getBuildPolicy() == BuildPolicy.CONSTRUCTOR) {
            if (rootClass.isIterable()) {
                return getVariablesForIterableConstructor(rootClass, fieldToVariableMap.values());
            }
            return getOrderedVariablesFromConstructor(rootClass, fieldToVariableMap.values());
        }
        return fieldToVariableMap.values().stream().map(Function.identity()).toList();
    }

    private List<Variable> getOrderedVariablesFromConstructor(
            Clazz rootClass,
            Collection<Variable> variables) {
        final var constClasses = rootClass.getConstructorClasses();
        var addedFields = new HashSet<Variable>();
        var orderedVariables = new ArrayList<Variable>(variables.size() * 2);

        for (var clazz : constClasses) {
            variables.stream()
                    .filter(var -> !addedFields.contains(var))
                    .filter(var -> var.getClazz().getClazz().equals(clazz))
                    .findFirst()
                    .ifPresent(var -> {
                        addedFields.add(var);
                        orderedVariables.add(var);
                    });
        }

        return orderedVariables;
    }

    private List<Variable> getVariablesForIterableConstructor(Clazz rootClass, Collection<Variable> variables) {
        final var firstVar = variables.stream().toList().getFirst();
        final var numVars = randomValueGenerator.randomIntRange(mockGenConfig.getIterableMinSize(),
                mockGenConfig.getIterableMaxSize());
        return Collections.nCopies(numVars, firstVar);
    }

}
