package com.musingscafe.commons.predicate;

import java.util.function.Supplier;

public class FunctionUtils {
    public static <R> R execute(Supplier<Boolean> predicate, Supplier<R> ifTrue, Supplier<R> ifFalse) {
        if(ObjectUtils.valueOf(predicate.get())) {
            return ifTrue.get();
        }
        return ifFalse.get();
    }

}
