package com.chocobo.composite.entity;

import com.chocobo.composite.util.ShipIdGenerator;

public class Ship extends Thread {

    private final long shipId;
    private int capacity;
    private State shipState;
    private Task task;

    public enum State {
        NEW,
        PROCESSING,
        FINISHED
    }

    public enum Task {
        LOADING,
        UNLOADING
    }

    public Ship(Task task) {
        this.shipId = ShipIdGenerator.generateId();
        this.shipState = State.NEW;
        this.task = task;
    }

    public long getShipId() {
        return shipId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public State getShipState() {
        return shipState;
    }

    public void setShipState(State shipState) {
        this.shipState = shipState;
    }

    @Override
    public void run() {
        Port port = Port.getInstance();
        Pier pier = port.getPier();
        pier.process(this);

        switch (task) {
            case LOADING -> port.removeFromStorage();
            case UNLOADING -> port.addToStorage();
        }

        port.releasePier(pier);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Ship: ");
        stringBuilder.append("id = ").append(shipId).append(", ");
        stringBuilder.append("state = ").append(shipState.name()).append(";");

        return stringBuilder.toString();
    }
}
