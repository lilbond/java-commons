package com.musingscafe.commons.predicate;

import java.util.function.Function;

public interface Conditional {
    boolean test();

    default <T, R> R whenTrue(Function<T, R> function, T t) {
        if (test()) {
            return function.apply(t);
        }

        return null;
    }
}
