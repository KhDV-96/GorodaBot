package com.onedayfirm.gorodabot.containers;

public class Phrases extends Container<String> {

    private static final String PATH = "phrases.txt";

    private static Phrases instance = new Phrases();

    private Phrases() {
        super(PATH);
    }

    public static Phrases getInstance() {
        return instance;
    }

    @Override
    String parse(String value) {
        return value;
    }
}
