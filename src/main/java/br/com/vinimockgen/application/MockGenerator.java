package br.com.vinimockgen.application;

import br.com.vinimockgen.domain.exception.NoInnerClassExceptions;
import br.com.vinimockgen.presentation.classes.financial.Portfolio;
import br.com.vinimockgen.presentation.config.MockGenConfiguration;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class MockGenerator {

    private final ClassScanner classScanner;
    private final Parser parser;

    public String generateMock(MockGenConfiguration mockGenConfig) throws NoInnerClassExceptions {
        final var scannedTree = classScanner.scan(Portfolio.class);
        final var parsedCode = parser.parse(scannedTree);
        System.out.println(parsedCode);
        return parsedCode;
    }

}
