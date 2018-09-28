package com.onedayfirm.gorodabot.bot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

class Phrases {

    private static final String PATH = "resources/phrases.txt";
    private static final String SEPARATOR = " ~?? ";

    private static Phrases instance = new Phrases();

    private HashMap<String, String> phrases;

    private Phrases() {
        phrases = new HashMap<>();
        loadPhrases().forEach(this::addPhrase);
    }

    static Phrases getInstance() {
        return instance;
    }

    String getPhrase(String key) {
        return phrases.get(key);
    }

    private List<String> loadPhrases() {
        try {
            return Files.readAllLines(Paths.get(PATH));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private void addPhrase(String phrase) {
        var blocks = phrase.split(SEPARATOR);
        phrases.put(blocks[0], blocks[1]);
    }
}
