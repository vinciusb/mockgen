package br.com.vinimockgen.domain;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
}
