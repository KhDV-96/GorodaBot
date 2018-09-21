package com.onedayfirm.gorodabot.bot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

class Phrases {

    private static final String PATH = "resources/phrases.txt";
    private static final String SEPARATOR = " ~ ";

    private static Phrases ourInstance = new Phrases();

    private HashMap<String, String> phrases;

    private Phrases() {
        phrases = new HashMap<>();
        try {
            parse(load());
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    static Phrases getInstance() {
        return ourInstance;
    }

    String getPhrase(String key) {
        return phrases.get(key);
    }

    private List<String> load() throws IOException {
        return Files.lines(Paths.get(PATH)).collect(Collectors.toList());
    }

    private void parse(List<String> lines) {
        lines.stream()
                .filter(s -> !s.isEmpty())
                .map(s -> s.split(SEPARATOR))
                .forEach(b -> phrases.put(b[0], b[1]));
    }
}
