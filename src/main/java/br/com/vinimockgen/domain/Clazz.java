package br.com.vinimockgen.domain;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class Clazz {

    private final static Set<Class> pseudoPrimiteClasses = new HashSet<Class>() {
        {
            add(Integer.class);
            add(String.class);
            add(Double.class);
            add(Boolean.class);
        }
    };

    private final Class clazz;
    private Map<String, Clazz> fields;
    private boolean isIterable;
    private boolean isGeneric;
    private BuildPolicy buildPolicy;

    public Clazz(Type type) {
        if (type instanceof ParameterizedType) {
            clazz = (Class<?>) ((ParameterizedType) type).getRawType();
            isGeneric = true;
        } else {
            clazz = (Class<?>) type;
        }

        if (clazz.isArray() || Iterable.class.isAssignableFrom(clazz)) {
            isIterable = true;
        }
    }

    public boolean isPseudoPrimitive() {
        return clazz.isPrimitive() || pseudoPrimiteClasses.contains(clazz);
    }

    public void initFields() {
        fields = new HashMap<>();
    }

    public void addField(String name, Clazz fieldClass) {
        fields.put(name, fieldClass);
    }

    public void addIterableField(Clazz iterFieldType) {
        fields.put("iter", iterFieldType);
    }

    public void setBuildPolicy() {
        if (tryToGetBuilder() || tryToGetConstructor() || tryToGetSetters()) {
            return;
        }
        throw new RuntimeException();
    }

    private boolean tryToGetBuilder() {
        try {
            if (clazz.getMethod("builder") != null) {
                this.buildPolicy = BuildPolicy.BUILDER;
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }

    private boolean tryToGetConstructor() {
        if (this.isPseudoPrimitive() || this.isIterable) {
            this.buildPolicy = BuildPolicy.CONSTRUCTOR;
            return true;
        }

        final var parameterSet = fields.values()
                .stream()
                .map(Clazz::getClazz)
                .collect(Collectors.toSet());
        final var constructors = clazz.getConstructors();

        for (var constructor : constructors) {
            final var constructorParameterSet = new HashSet<Class>() {
                {
                    addAll(List.of(constructor.getParameterTypes()));
                }
            };

            if (constructorParameterSet.containsAll(parameterSet)
                    && parameterSet.containsAll(constructorParameterSet)) {
                this.buildPolicy = BuildPolicy.CONSTRUCTOR;
                return true;
            }
        }
        return false;
    }

    private boolean tryToGetSetters() {
        return false;
    }

}
