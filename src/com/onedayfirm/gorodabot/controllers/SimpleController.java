package com.onedayfirm.gorodabot.controllers;

import com.onedayfirm.gorodabot.bot.Bot;
import com.onedayfirm.gorodabot.clients.Client;

import java.util.LinkedList;

public class SimpleController implements Controller {

    private Bot bot;

    public SimpleController(Bot bot, Client... clients) {
        this.bot = bot;
        for (var client : clients)
            client.setController(this);
    }

    public void handleConnection(Client client, int id) {
        var responses = new LinkedList<String>();
        bot.onConnection(id, responses);
        responses.forEach(text -> client.send(id, text));
    }

    public void handleMessage(Client client, int id, String message) {
        if (!bot.isUserConnected(id))
            handleConnection(client, id);
        var responses = new LinkedList<String>();
        bot.onMessage(id, message, responses);
        responses.forEach(text -> client.send(id, text));
    }
}
