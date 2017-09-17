package com.musingscafe.commons.api;

/**
 * Created by ayadav on 9/13/17.
 */
@FunctionalInterface
public interface CheckedSupplier<R> {
    R get() throws Throwable;
}
