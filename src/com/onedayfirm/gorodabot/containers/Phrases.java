package com.onedayfirm.gorodabot.containers;

import com.onedayfirm.gorodabot.io.FileReader;

import java.util.HashMap;
import java.util.Map;

public class Phrases {

    private static final String PATH = "resources/phrases.txt";
    private static final String SEPARATOR = " ~?? ";

    private static Phrases instance = new Phrases();

    private Map<String, String> phrases;

    private Phrases() {
        phrases = new HashMap<>();
        FileReader.readLines(PATH).forEach(this::addPhrase);
    }

    public static Phrases getInstance() {
        return instance;
    }

    public String getPhrase(String key) {
        return phrases.get(key);
    }

    private void addPhrase(String line) {
        var blocks = line.split(SEPARATOR);
        phrases.put(blocks[0], blocks[1]);
    }
}
