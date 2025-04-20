package br.com.vinimockgen.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.vinimockgen.domain.Clazz;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class Parser {

    private List<String> codeLines;

    public String parse(Clazz rootClass) {
        codeLines = new ArrayList<>();
        parse(rootClass, rootClass.getClazz().getTypeName());
        return codeLines.stream().collect(Collectors.joining(System.lineSeparator()));
    }

    private void parse(Clazz rootClass, String propertyName) {
        try {
            if (rootClass.isPseudoPrimitive()) {
                declareVariable(rootClass, propertyName);
                return;
            }

            for (var entry : rootClass.getFields().entrySet()) {
                parse(entry.getValue(), entry.getKey());
            }

            declareVariable(rootClass, propertyName);
        } catch (Exception ex) {
            log.error(String.format(
                    "Error while trying to parse the Hierarchy Tree. Error happend for the \n{ \n\t\"propertyName\": \"%s\",\n\t\"class\": \"%s\"}\n",
                    propertyName, rootClass),
                    ex);
            throw ex;
        }

    }

    private void declareVariable(Clazz clazz, String propertyName) {
        codeLines.add(String.format("var %s = new %s();", propertyName, clazz.getClazz().getName()));
    }
}
