package br.com.vinimockgen.application;

import jakarta.inject.Singleton;

@Singleton
public class RandomValueGenerator {

    public int randomIntRange(int start, int end) {
        return (int) Math.random() * (end - start) + start;
    }

}
