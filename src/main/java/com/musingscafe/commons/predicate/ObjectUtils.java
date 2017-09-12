package com.musingscafe.commons.predicate;

public class ObjectUtils {
    private ObjectUtils() {}

    public static boolean valueOf(Boolean object) {
        return object != null && object;
    }

    public static int valueOf(Integer integer) {
        if (integer == null) {
            return Integer.MIN_VALUE;
        }
        return integer;
    }

    public static boolean valueOrDefault(Boolean object, boolean defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        return object;
    }

    public static int valueOrDefault(Integer integer, int defaultValue) {
        if (integer == null) {
            return defaultValue;
        }

        return integer;
    }
}
