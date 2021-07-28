package com.chocobo.composite.util;

public class ShipIdGenerator {

    private static long counter;

    private ShipIdGenerator() { }

    public static long generateId() {
        return ++counter;
    }
}
