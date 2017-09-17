package com.musingscafe.commons.function;

import com.lambdista.util.FailableSupplier;
import com.musingscafe.commons.api.Worker;

/**
 * Created by ayadav on 9/17/17.
 */
public class Try {

    public static <R> com.lambdista.util.Try<R> apply(FailableSupplier<R> supplier) {
        return com.lambdista.util.Try.apply(supplier);
    }

    public static <R> ExecTry<R> apply(Worker worker) {
        return ExecTry.apply(worker);
    }
}
