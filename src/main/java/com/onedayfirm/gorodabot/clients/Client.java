package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.bot.Bot;
import com.onedayfirm.gorodabot.bot.Session;

import java.util.LinkedList;

public abstract class Client implements Runnable {

    Bot bot;

    Client(Bot bot) {
        this.bot = bot;
    }

    public abstract void run();

    abstract void send(long id, String message);

    void handleConnection(Session session) {
        var responses = new LinkedList<String>();
        bot.onConnection(session, responses);
        responses.forEach(text -> send(session.getId(), text));
    }

    void handleMessage(long id, String message) {
        if (message.isEmpty())
            return;
        var responses = new LinkedList<String>();
        bot.onMessage(id, message, responses);
        responses.forEach(text -> send(id, text));
    }
}
