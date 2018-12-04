package com.onedayfirm.gorodabot.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileReader {

    public static List<String> readLines(String path) {
        try (var in = new BufferedReader(openForReading(path))) {
            return in.lines().collect(Collectors.toList());
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static Reader openForReading(String path) {
        var stream = FileReader.class.getClassLoader().getResourceAsStream(path);
        return new InputStreamReader(Objects.requireNonNull(stream), StandardCharsets.UTF_8);
    }
}
