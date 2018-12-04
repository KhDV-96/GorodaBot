package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.utils.Configurations;

import java.util.Collection;
import java.util.LinkedList;

public abstract class CommandHandler implements Handler {

    private static final String COMMAND_PREFIX = Configurations.getProperty("commandHandler.prefix");

    private Collection<String> commands;

    CommandHandler() {
        commands = new LinkedList<>();
    }

    public static boolean isCommand(String string) {
        return string.startsWith(COMMAND_PREFIX);
    }

    public final Collection<String> getCommands() {
        return commands;
    }

    void addCommand(String command) {
        commands.add(command);
    }
}
