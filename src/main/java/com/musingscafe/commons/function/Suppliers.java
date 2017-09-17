package com.musingscafe.commons.function;

import com.musingscafe.commons.object.Obj;

import java.util.function.Supplier;

public abstract class Suppliers<T> {

    private Suppliers() {}

    public abstract boolean isSuccess();
    public abstract T get();
    public abstract T getOrElse(T defaultValue);
    public abstract Suppliers<T> orElse(Supplier<Boolean> predicate, Supplier<T> supplier);

    public static <R> Suppliers<R> apply(Supplier<Boolean> predicate, Supplier<R> supplier) {
      if(predicate.get()) {
          return new SupplierSuccess<R>(supplier.get());
      }

      return new SupplierElse<R>();
    }

    public static final class SupplierElse<R> extends Suppliers<R> {

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public R get() {
            throw new UnsupportedOperationException("Get is not supported on SupplierElse");
        }

        @Override
        public R getOrElse(R defaultValue) {
            return defaultValue;
        }

        @Override
        public Suppliers<R> orElse(Supplier<Boolean> predicate, Supplier<R> supplier) {
            return apply(predicate, supplier);
        }
    }

    public static final class SupplierSuccess<R> extends Suppliers<R> {
        private final R value;

        public SupplierSuccess(R value) {
            this.value = value;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public R get() {
            return value;
        }

        @Override
        public R getOrElse(R defaultValue) {
            return Obj.getOrElse(value, defaultValue);
        }

        @Override
        public Suppliers<R> orElse(Supplier<Boolean> predicate, Supplier<R> supplier) {
            return (Suppliers<R>) this;
        }
    }
}
