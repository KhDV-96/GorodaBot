package com.onedayfirm.gorodabot.containers;

import java.util.Arrays;
import java.util.Collection;

public class Commands extends Container<Collection<String>> {

    private static final String PATH = "commands.txt";
    private static final String SEPARATOR = "\\s*,\\s*";

    private static Commands instance = new Commands();

    private Commands() {
        super(PATH);
    }

    public static Commands getInstance() {
        return instance;
    }

    @Override
    Collection<String> parse(String value) {
        return Arrays.asList(value.split(SEPARATOR));
    }
}
