package com.onedayfirm.gorodabot;

import com.onedayfirm.gorodabot.bot.GorodaBot;
import com.onedayfirm.gorodabot.clients.ConsoleClient;
import com.onedayfirm.gorodabot.goroda.KladrCitiesStorage;
import com.onedayfirm.gorodabot.handlers.GorodaGameTurnHandler;
import com.onedayfirm.gorodabot.handlers.HelpCommandHandler;
import com.onedayfirm.gorodabot.handlers.InfoCommandHandler;
import com.onedayfirm.gorodabot.handlers.StartGorodaGameCommandHandler;
import com.onedayfirm.gorodabot.mediawiki.MediaWiki;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            var commandHandlers = List.of(
                    new HelpCommandHandler(),
                    new StartGorodaGameCommandHandler(KladrCitiesStorage.getInstance()),
                    new InfoCommandHandler(new MediaWiki())
            );
            var messageHandlers = List.of(new GorodaGameTurnHandler());
            var bot = new GorodaBot(commandHandlers, messageHandlers);
            var client = new ConsoleClient(bot);
            client.run();
        } catch (ExitException exception) {
            exception.printStackTrace();
            System.exit(exception.status);
        }
    }
}
