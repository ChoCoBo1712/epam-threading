package com.chocobo.composite._main;

import com.chocobo.composite.entity.Ship;
import com.chocobo.composite.exception.ThreadingException;
import com.chocobo.composite.parser.ShipLinesParser;
import com.chocobo.composite.reader.ShipLinesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {

    public static final Logger logger = LogManager.getLogger();
    private static final String FILE_PATH = "data/info.txt";

    public static void main(String[] args) {
        try {
            ShipLinesReader reader = new ShipLinesReader();
            ShipLinesParser parser = new ShipLinesParser();

            List<Ship> ships = reader.readLinesToList(FILE_PATH).stream()
                    .map(parser::parse)
                    .collect(Collectors.toList());

            ExecutorService executorService = Executors.newFixedThreadPool(ships.size());
            ships.forEach(executorService::execute);
            executorService.shutdown();
        } catch (ThreadingException e) {
            logger.error("Unexpected exception: ", e);
        }
    }
}
