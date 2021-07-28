package com.chocobo.composite.parser;

import com.chocobo.composite.entity.Ship;

public class ShipLinesParser {

    public Ship parse(String line) {
        Ship.Task task = Ship.Task.valueOf(line);
        return new Ship(task);
    }
}
