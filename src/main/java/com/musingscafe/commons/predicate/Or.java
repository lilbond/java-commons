package com.musingscafe.commons.predicate;

import com.musingscafe.commons.api.Conditional;

import java.util.Arrays;
import java.util.Objects;

public class Or implements Conditional {
    private final Conditional[] conditionals;
    public Or(Conditional... conditionals) {
        this.conditionals = conditionals;
        Objects.requireNonNull(this.conditionals);
    }

    @Override
    public boolean test() {
        return Arrays.stream(conditionals).filter(c -> c.test()).findFirst().isPresent();
    }
}
