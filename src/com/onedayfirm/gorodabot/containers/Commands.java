package com.onedayfirm.gorodabot.containers;

import com.onedayfirm.gorodabot.io.FileReader;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Commands {

    private static final String PATH = "resources/commands.txt";
    private static final String SEPARATOR = " ~?? ";
    private static final String COMMAND_SEPARATOR = "\\s*,\\s*";

    private static Commands instance = new Commands();

    private Map<String, Collection<String>> commands;

    private Commands() {
        commands = new HashMap<>();
        FileReader.readLines(PATH).forEach(this::addCommands);
    }

    public static Commands getInstance() {
        return instance;
    }

    public Collection<String> getCommands(String key) {
        return commands.get(key);
    }

    private void addCommands(String line) {
        var blocks = line.split(SEPARATOR);
        var entries = Arrays.asList(blocks[1].split(COMMAND_SEPARATOR));
        commands.put(blocks[0], entries);
    }
}
