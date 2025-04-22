package br.com.vinimockgen.application;

import br.com.vinimockgen.domain.exception.NoInnerClassExceptions;
import br.com.vinimockgen.domain.exception.NotMappedBuilderPolicyException;
import br.com.vinimockgen.presentation.classes.financial.Portfolio;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class MockGenerator {

    private final ClassScanner classScanner;
    private final VariableGenerator variableGenerator;
    private final Parser parser;

    public String generateMock() throws NoInnerClassExceptions, NotMappedBuilderPolicyException {
        final var scannedTree = classScanner.scan(Portfolio.class);
        final var variableTree = variableGenerator.generateVariables(scannedTree);
        final var parsedCode = parser.parse(scannedTree);
        System.out.println(parsedCode);
        return parsedCode;
    }

}
