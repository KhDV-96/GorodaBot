package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.bot.Bot;

import java.util.LinkedList;

public abstract class Client implements Runnable {

    private Bot bot;

    Client(Bot bot) {
        this.bot = bot;
    }

    public abstract void run();

    abstract void send(int id, String message);

    void handleConnection(int id) {
        var responses = new LinkedList<String>();
        bot.onConnection(id, responses);
        responses.forEach(text -> send(id, text));
    }

    void handleMessage(int id, String message) {
        if (message.isEmpty())
            return;
        var responses = new LinkedList<String>();
        bot.onMessage(id, message, responses);
        responses.forEach(text -> send(id, text));
    }
}
