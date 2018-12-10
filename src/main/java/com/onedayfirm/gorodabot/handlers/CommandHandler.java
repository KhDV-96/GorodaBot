package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.containers.Commands;
import com.onedayfirm.gorodabot.utils.Configurations;

import java.util.Collection;

public abstract class CommandHandler implements Handler {

    private static final String COMMAND_PREFIX = Configurations.getProperty("commandHandler.prefix");

    private Collection<String> commands;

    CommandHandler(String commandName) {
        commands = Commands.getInstance().get(commandName);
    }

    public static boolean isCommand(String string) {
        return string.startsWith(COMMAND_PREFIX);
    }

    public final Collection<String> getCommands() {
        return commands;
    }
}
