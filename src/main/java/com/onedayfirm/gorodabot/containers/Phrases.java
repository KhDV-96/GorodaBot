package com.onedayfirm.gorodabot.containers;

import com.onedayfirm.gorodabot.utils.Configurations;

public class Phrases extends Container<String> {

    private static final String PATH = Configurations.getProperty("phrases.file");

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
