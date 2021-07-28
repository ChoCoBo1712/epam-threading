package com.chocobo.composite.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.chocobo.composite.util.PierIdGenerator;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Pier {

    public static final Logger logger = LogManager.getLogger();
    private static final int MIN_MILLISECONDS = 100;
    private static final int MAX_MILLISECONDS = 1000;

    private final long pierId;

    public Pier() {
        pierId = PierIdGenerator.generateId();
    }

    public long getPierId() {
        return pierId;
    }

    public void process(Ship ship) {
        ship.setShipState(Ship.State.PROCESSING);
        long shipId = ship.getShipId();
        logger.info("Pier " + pierId + " started processing ship " + shipId);

        Random random = new Random();
        long millisecondsCost = random.nextInt(MAX_MILLISECONDS - MIN_MILLISECONDS) + MIN_MILLISECONDS;
        try {
            TimeUnit.MILLISECONDS.sleep(millisecondsCost);
        } catch (InterruptedException e) {
            logger.error("Thread exception: ", e);
            Thread.currentThread().interrupt();
        }

        ship.setShipState(Ship.State.FINISHED);
        logger.info("Pier " + pierId + " finished processing ship " + shipId);
    }

    @Override
    public String toString() {
        return "Pier " + pierId;
    }
}
