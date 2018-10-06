package com.onedayfirm.gorodabot;

import com.onedayfirm.gorodabot.bot.GorodaBot;
import com.onedayfirm.gorodabot.clients.ConsoleClient;
import com.onedayfirm.gorodabot.controllers.SimpleController;
import com.onedayfirm.gorodabot.handlers.CompoundHandlerBuilder;
import com.onedayfirm.gorodabot.handlers.GorodaGameTurnHandler;
import com.onedayfirm.gorodabot.handlers.HelpCommandHandler;
import com.onedayfirm.gorodabot.handlers.StartGorodaGameCommandHandler;

public class Main {

    public static void main(String[] args) {
        var commandHandler = new CompoundHandlerBuilder()
                .add(new HelpCommandHandler())
                .add(new StartGorodaGameCommandHandler())
                .build();
        var messageHandler = new GorodaGameTurnHandler();
        var bot = new GorodaBot(commandHandler, messageHandler);
        var client = new ConsoleClient();
        new SimpleController(bot, client);
        client.run();
    }
}
