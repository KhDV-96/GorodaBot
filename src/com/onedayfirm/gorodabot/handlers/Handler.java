package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Phrases;
import com.onedayfirm.gorodabot.bot.Session;

import java.util.Collection;

public abstract class Handler {

    private Handler next;

    Handler getNext() {
        return next;
    }

    Handler setNext(Handler next) {
        return this.next = next;
    }

    void onUnhandled(Collection<String> responses) {
        responses.add(Phrases.getInstance().getPhrase("UNKNOWN COMMAND"));
        responses.add(Phrases.getInstance().getPhrase("HELP"));
    }

    public abstract void handle(String message, Session session, Collection<String> responses);

    abstract void reply(String message, Session session, Collection<String> responses);
}
