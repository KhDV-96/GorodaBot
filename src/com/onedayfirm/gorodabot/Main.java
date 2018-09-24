package com.onedayfirm.gorodabot;

import com.onedayfirm.gorodabot.bot.GorodaBot;
import com.onedayfirm.gorodabot.clients.TerminalClient;
import com.onedayfirm.gorodabot.controllers.SimpleController;

public class Main {

    public static void main(String[] args) {
        var bot = new GorodaBot();
        var client = new TerminalClient();
        new SimpleController(bot, client);
        client.receive();
    }
}
