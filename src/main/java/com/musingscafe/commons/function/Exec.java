package com.musingscafe.commons.function;

import com.musingscafe.commons.api.Worker;

import java.util.Objects;
import java.util.function.Consumer;
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

    public static <R> Exec<R> applyWithTry(Supplier<Boolean> predicate, Worker worker, Consumer<? super Throwable> recoverWith) {
        if(Try.apply(predicate::get).getOrElse(false)) {
            return new ExecSuccess<>(worker, true, recoverWith);
        }

        return new ExecElse<>(worker);
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
        private final boolean withTry;
        private final Consumer<? super Throwable> recoverFunc;

        public ExecSuccess(Worker worker) {
            this(worker, false, null);
        }

        public ExecSuccess(Worker worker, boolean withTry, Consumer<? super Throwable> recoverFunc) {
            this.worker = worker;
            this.withTry = withTry;
            this.recoverFunc = recoverFunc;
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
            if (withTry && Objects.nonNull(recoverFunc)) {
                Try.apply(worker).recoverWith(recoverFunc);
                return;
            }

            if (withTry) {
                Try.apply(worker);
                return;
            }

            worker.execute();
        }
    }
}
