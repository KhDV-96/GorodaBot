package com.onedayfirm.gorodabot.controllers;

import com.onedayfirm.gorodabot.bot.Bot;
import com.onedayfirm.gorodabot.clients.Client;

public class SimpleController implements Controller {

    private Bot bot;

    public SimpleController(Bot bot, Client... clients) {
        this.bot = bot;
        for (var client : clients)
            client.setController(this);
    }

    public void handleConnection(Client client, int id) {
        for (var message : bot.onConnection(id))
            client.send(id, message);
    }

    public void handleMessage(Client client, int id, String message) {
        if (!bot.isUserConnected(id))
            handleConnection(client, id);
        for (var text : bot.onMessage(id, message))
            client.send(id, text);
    }
}
