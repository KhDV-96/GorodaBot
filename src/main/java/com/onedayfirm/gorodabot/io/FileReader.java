package com.onedayfirm.gorodabot.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {

    public static List<String> readLines(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
