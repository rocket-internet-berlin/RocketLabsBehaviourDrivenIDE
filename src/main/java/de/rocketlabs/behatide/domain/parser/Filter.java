package de.rocketlabs.behatide.domain.parser;

public interface Filter<T> {

    boolean isMatch(T data);
}
