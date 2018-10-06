package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;

import java.util.Collection;

public abstract class CommandHandler extends Handler {

    private static final String COMMAND_PREFIX = "/";

    private String command;

    CommandHandler(String command) {
        this.command = command;
    }

    @Override
    public void handle(String message, Session session, Collection<String> responses) {
        if (command.equals(message))
            reply(message, session, responses);
        else if (getNext() == null)
            onUnhandled(responses);
        else
            getNext().handle(message, session, responses);
    }

    public static boolean isCommand(String str) {
        return str.startsWith(COMMAND_PREFIX);
    }
}
