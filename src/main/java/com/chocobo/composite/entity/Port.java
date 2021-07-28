package com.chocobo.composite.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {

    private static final Logger logger = LogManager.getLogger();
    private static Port instance;
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static final int DEFAULT_PIERS_COUNT = 1;

    private final Lock pierLock = new ReentrantLock();
    private final List<Pier> pierPool = new ArrayList<>();
    private final List<Pier> usedPiers = new ArrayList<>();
    private final Queue<Condition> conditionsQueue = new ArrayDeque<>();

    private final AtomicInteger usedStorage = new AtomicInteger(0);

    private Port() {

        for (int i = 0; i < DEFAULT_PIERS_COUNT; i++) {
            pierPool.add(new Pier());
        }
    }

    public static Port getInstance() {
        while (instance == null) {
            if (isInitialized.compareAndSet(false, true)) {
                instance = new Port();
            }
        }
        return instance;
    }

    public Pier getPier() {
        try {
            pierLock.lock();

            if (pierPool.isEmpty()) {
                Condition condition = pierLock.newCondition();
                conditionsQueue.add(condition);
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    logger.error("Thread exception: ", e);
                    Thread.currentThread().interrupt();
                }
            }

            Pier pier = pierPool.remove(pierPool.size() - 1);
            usedPiers.add(pier);
            return pier;
        } finally {
            pierLock.unlock();
        }
    }

    public boolean releasePier(Pier pier) {
        try {
            pierLock.lock();

            if (!usedPiers.isEmpty()) {
                pierPool.add(pier);
                Condition condition = conditionsQueue.poll();
                if (condition != null) {
                    condition.signal();
                }
            }

            return usedPiers.remove(pier);
        }
        finally {
            pierLock.unlock();
        }
    }

    public void addToStorage() {
        usedStorage.incrementAndGet();
    }

    public void removeFromStorage() {
        usedStorage.decrementAndGet();
    }
}
