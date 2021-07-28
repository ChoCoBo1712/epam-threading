package com.chocobo.composite.reader;

import com.chocobo.composite.exception.ThreadingException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ShipLinesReader {

    public List<String> readLinesToList(String filePath) throws ThreadingException {
        try {
            return Files.lines(Path.of(ClassLoader.getSystemResource(filePath).toURI()))
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException | NullPointerException e) {
            throw new ThreadingException(e);
        }
    }
}
