package com.musingscafe.commons.predicate;

import java.util.Objects;

/**
 * Created by ayadav on 2/17/17.
 */
public class Condition {
    Conditional conditional;

    public static Condition test(Conditional conditional) {
        Condition condition = new Condition();
        condition.conditional = conditional;
        return condition;
    }

    public Condition and(Conditional andConditional) {
        Objects.requireNonNull(conditional);
        And and = new And(conditional, andConditional);
        conditional = and;
        return this;
    }

    public Condition or(Conditional orConditional) {
        Objects.requireNonNull(conditional);
        Or or = new Or(conditional, orConditional);
        conditional = or;
        return this;
    }

    public Conditional build() {
        return conditional;
    }

    public static Condition builder() {
        return new Condition();
    }
}
