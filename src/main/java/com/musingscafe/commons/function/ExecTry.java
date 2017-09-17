package com.musingscafe.commons.function;

import com.musingscafe.commons.api.Worker;

import java.util.function.Consumer;

/**
 * Created by ayadav on 9/17/17.
 */
public abstract class ExecTry<T> {

    public abstract boolean isSuccess();
    public abstract ExecTry<T> orElse(Worker worker);
    public abstract void recoverWith(Consumer<? super Throwable> consumer);

    public static <R> ExecTry<R> apply(Worker worker) {
        try {
            worker.execute();
            return new Success<R>();
        }
        catch (Throwable throwable) {
            return new Failure<R>(throwable);
        }
    }

    public static final class Success<T> extends ExecTry<T> {

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public ExecTry<T> orElse(Worker worker) {
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void recoverWith(Consumer<? super Throwable> consumer) {
           // do nothing
        }
    }

    public static final class Failure<T> extends ExecTry<T> {
        private final Throwable throwable;

        public Failure(Throwable throwable) {
            this.throwable = throwable;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public ExecTry<T> orElse(Worker worker) {
            return apply(worker);
        }

        @Override
        public void recoverWith(Consumer<? super Throwable> consumer) {
            consumer.accept(throwable);
        }
    }
}
