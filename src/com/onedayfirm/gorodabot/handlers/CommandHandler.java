package com.onedayfirm.gorodabot.handlers;

import java.util.Collection;
import java.util.LinkedList;

public abstract class CommandHandler implements Handler {

    private Collection<String> commands;

    CommandHandler() {
        commands = new LinkedList<>();
    }

    public final Collection<String> getCommands() {
        return commands;
    }

    void addCommand(String command) {
        commands.add(command);
    }
}
