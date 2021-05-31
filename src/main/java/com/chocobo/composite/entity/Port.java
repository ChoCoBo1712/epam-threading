package com.chocobo.composite.entity;

import java.util.concurrent.atomic.AtomicBoolean;

public class Port {

    private static Port instance;
    private static final AtomicBoolean instanceInitialized = new AtomicBoolean(false);

    private Port() {

    }

    public static Port getInstance() {
        while (instance == null) {
            if (instanceInitialized.compareAndSet(false, true)) {
                instance = new Port();
            }
        }

        return instance;
    }
}
