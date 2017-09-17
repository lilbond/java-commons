package com.musingscafe.commons.object;

import java.util.Optional;
import java.util.function.Supplier;

public class Obj {
    private Obj() {}

    public static boolean valueOf(Boolean object) {
        return object != null && object;
    }

    public static int valueOf(Integer integer) {
        if (integer == null) {
            return Integer.MIN_VALUE;
        }
        return integer;
    }

    public static boolean getOrElse(Boolean object, boolean defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        return object;
    }

    public static int getOrElse(Integer integer, int defaultValue) {
        if (integer == null) {
            return defaultValue;
        }

        return integer;
    }

    public static <T> T getOrElse(T object, T defaultValue) {
        if (object == null) {
            return defaultValue;
        }

        return object;
    }

    public static <T> T getOrElse(T object, Supplier<T> supplier) {
        if (object == null) {
            return supplier.get();
        }

        return object;
    }

    public static <T> Optional<T> toOptional(T object) {
        return Optional.ofNullable(object);
    }

    public static <T> T getOrElse(Optional<T> optional, T defaultValue) {
        if (optional.isPresent()) {
            return optional.get();
        }

        return defaultValue;
    }

    public static <T> T getOrElse(Optional<T> optional, Supplier<T> other) {
        if (optional.isPresent()) {
            return optional.get();
        }

        return other.get();
    }
}
