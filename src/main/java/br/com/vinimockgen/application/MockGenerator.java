package br.com.vinimockgen.application;

import br.com.vinimockgen.domain.exception.NoInnerClassExceptions;
import br.com.vinimockgen.domain.exception.NotMappedBuilderPolicyException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class MockGenerator {

    private final ClassScanner classScanner;
    private final VariableGenerator variableGenerator;
    private final Parser parser;

    public String generateMock(Class<?> clazz) throws NoInnerClassExceptions, NotMappedBuilderPolicyException {
        final var scannedTree = classScanner.scan(clazz);
        final var variableTree = variableGenerator.generateVariables(scannedTree);
        final var parsedCode = parser.parse(variableTree);
        System.out.println(parsedCode);
        return parsedCode;
    }

}
