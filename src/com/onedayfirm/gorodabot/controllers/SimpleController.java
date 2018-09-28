package com.onedayfirm.gorodabot.controllers;

import com.onedayfirm.gorodabot.bot.Bot;
import com.onedayfirm.gorodabot.clients.Client;

import java.util.LinkedList;

public class SimpleController implements Controller {

    private Bot bot;
    private Client client;

    public SimpleController(Bot bot, Client client) {
        this.bot = bot;
        this.client = client;
        client.setController(this);
        client.run();
    }

    public void handleConnection(int id) {
        var responses = new LinkedList<String>();
        bot.onConnection(id, responses);
        responses.forEach(text -> client.send(id, text));
    }

    public void handleMessage(int id, String message) {
        if (message.isEmpty())
            return;
        var responses = new LinkedList<String>();
        bot.onMessage(id, message, responses);
        responses.forEach(text -> client.send(id, text));
    }
}
