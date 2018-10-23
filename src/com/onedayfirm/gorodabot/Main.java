package com.onedayfirm.gorodabot;

import com.onedayfirm.gorodabot.bot.GorodaBot;
import com.onedayfirm.gorodabot.clients.ConsoleClient;
import com.onedayfirm.gorodabot.handlers.GorodaGameTurnHandler;
import com.onedayfirm.gorodabot.handlers.HelpCommandHandler;
import com.onedayfirm.gorodabot.handlers.MessageHandler;
import com.onedayfirm.gorodabot.handlers.StartGorodaGameCommandHandler;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        var commandHandlers = List.of(new HelpCommandHandler(), new StartGorodaGameCommandHandler());
        var messageHandlers = List.of((MessageHandler) new GorodaGameTurnHandler());
        var bot = new GorodaBot(commandHandlers, messageHandlers);
        var client = new ConsoleClient(bot);
        client.run();
    }
}
