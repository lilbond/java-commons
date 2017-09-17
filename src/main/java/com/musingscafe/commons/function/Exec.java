package com.musingscafe.commons.function;

import com.musingscafe.commons.api.Worker;
import com.musingscafe.commons.object.Obj;

import java.util.function.Supplier;

/**
 * Created by ayadav on 9/17/17.
 */
public abstract class Exec<T> {

    public abstract boolean isSuccess();
    public abstract Exec<T> orElse(Supplier<Boolean> predicate, Worker worker);
    public abstract void execute();


    public static <R> Exec<R> apply(Supplier<Boolean> predicate, Worker worker) {
        if(predicate.get()) {
            return new ExecSuccess<>(worker);
        }

        return new ExecElse<>(worker);
    }

    public static <R> void testAndExecute(Supplier<Boolean> predicate, Worker ifTrue, Worker ifFalse) {
        if(Obj.valueOf(predicate.get())) {
            ifTrue.execute();
            return;
        }
        ifFalse.execute();
    }

    public static final class ExecElse<R> extends Exec<R> {
        private final Worker worker;

        public ExecElse(Worker worker) {
            this.worker = worker;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public Exec<R> orElse(Supplier<Boolean> predicate, Worker worker) {
            return apply(predicate, worker);
        }

        @Override
        public void execute() {
            // do nothing
        }
    }

    public static final class ExecSuccess<R> extends Exec<R> {
        private final Worker worker;

        public ExecSuccess(Worker worker) {
            this.worker = worker;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public Exec<R> orElse(Supplier<Boolean> predicate, Worker worker) {
            return this;
        }

        @Override
        public void execute() {
            worker.execute();
        }
    }
}
