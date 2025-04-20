package br.com.vinimockgen.application;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import br.com.vinimockgen.domain.Clazz;
import br.com.vinimockgen.domain.exception.NoInnerClassExceptions;
import jakarta.inject.Singleton;

@Singleton
public class ClassScanner {

    public Clazz scan(Type rootType) throws NoInnerClassExceptions {
        var clazz = new Clazz(rootType);

        if (clazz.isPseudoPrimitive())
            return clazz;

        clazz.initFields();

        if (clazz.isGeneric()) {
            final var typeArguments = ((ParameterizedType) rootType).getActualTypeArguments();
            for (int i = 0; i < typeArguments.length; i++) {
                this.addGenericType(clazz, typeArguments[i], i);
            }
        } else {
            for (var field : clazz.getClazz().getDeclaredFields()) {
                this.addField(clazz, field);
            }
        }

        clazz.setBuildPolicy();

        return clazz;
    }

    private void addField(Clazz clazz, Field field) throws NoInnerClassExceptions {
        final var genericType = field.getGenericType();
        final var fieldClass = scan(genericType);
        clazz.addField(field.getName(), fieldClass);
    }

    private void addGenericType(Clazz clazz, Type type, int idx) throws NoInnerClassExceptions {
        final var typeClazz = scan(type);
        clazz.addField("genArg" + idx, typeClazz);
    }
}
