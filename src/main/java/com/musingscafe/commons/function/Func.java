package com.musingscafe.commons.function;

import com.musingscafe.commons.api.CheckedSupplier;
import com.musingscafe.commons.api.Worker;
import com.musingscafe.commons.object.Obj;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by ayadav on 9/17/17.
 */
public class Func {
    private Func() {}

    public static <R> Exec<R> apply(Supplier<Boolean> predicate, Worker worker) {
        return Exec.apply(predicate, worker);
    }

    public static <R> Exec<R> applyWithTry(Supplier<Boolean> predicate, Worker worker, Consumer<? super Throwable> recoverFunc) {
        return Exec.applyWithTry(predicate, worker, recoverFunc);
    }

    public static <R> Suppliers<R> apply(Supplier<Boolean> predicate, Supplier<R> supplier) {
        return Suppliers.apply(predicate, supplier);
    }

    public static <R> R supply(Supplier<Boolean> predicate, Supplier<R> ifTrue, Supplier<R> ifFalse) {
        if(Obj.valueOf(predicate.get())) {
            return ifTrue.get();
        }
        return ifFalse.get();
    }

    public static <R> void execute(Supplier<Boolean> predicate, Worker ifTrue, Worker ifFalse) {
        if(Obj.valueOf(predicate.get())) {
            ifTrue.execute();
            return;
        }
        ifFalse.execute();
    }

    public static <R> R supply(CheckedSupplier<Boolean> predicate, Supplier<R> ifTrue, Supplier<R> ifFalse, Supplier<R> onException) {

        try {
            if(Obj.valueOf(predicate.get())) {
                return ifTrue.get();
            }
            return ifFalse.get();

        } catch (Throwable e) {
            return onException.get();
        }
    }

    public static <R> R supply(CheckedSupplier<Boolean> predicate, Supplier<R> ifTrue, Supplier<R> ifFalse, Function<Throwable, R> onException) {
        try {
            if(Obj.valueOf(predicate.get())) {
                return ifTrue.get();
            }
            return ifFalse.get();
        } catch (Throwable e) {
            return onException.apply(e);
        }
    }
}
