package de.rocketlabs.behatide.domain.model;

import java.util.List;

public interface Statement {

    List<String> getKeywords();

    String getExpression();
}
