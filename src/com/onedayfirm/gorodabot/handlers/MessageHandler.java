package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;

import java.util.Collection;

public abstract class MessageHandler extends Handler {

    @Override
    public void handle(String message, Session session, Collection<String> responses) {
        if (canHandle(message, session))
            reply(message, session, responses);
        else if (getNext() == null)
            onUnhandled(responses);
        else
            getNext().handle(message, session, responses);
    }

    abstract boolean canHandle(String message, Session session);
}
