package com.chocobo.composite.util;

public class PierIdGenerator {

    private static long counter;

    private PierIdGenerator() { }

    public static long generateId() {
        return ++counter;
    }
}
