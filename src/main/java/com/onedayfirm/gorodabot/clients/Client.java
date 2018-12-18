package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.bot.Bot;
import com.onedayfirm.gorodabot.bot.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public abstract class Client implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    Bot bot;

    Client(Bot bot) {
        this.bot = bot;
    }

    public abstract void run();

    abstract void send(long id, String message);

    void handleConnection(Session session) {
        LOGGER.info("User {id={}} connected", session.getId());
        var responses = new LinkedList<String>();
        bot.onConnection(session, responses);
        responses.forEach(text -> send(session.getId(), text));
    }

    void handleMessage(long id, String message) {
        if (message.isEmpty())
            return;
        LOGGER.info("Received message: {id={}, message='{}'}", id, message);
        var responses = new LinkedList<String>();
        bot.onMessage(id, message, responses);
        responses.forEach(text -> send(id, text));
    }
}
