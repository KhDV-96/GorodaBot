package com.onedayfirm.gorodabot;

import com.onedayfirm.gorodabot.bot.GorodaBot;
import com.onedayfirm.gorodabot.clients.TelegramClient;
import com.onedayfirm.gorodabot.goroda.KladrCitiesStorage;
import com.onedayfirm.gorodabot.handlers.GorodaGameTurnHandler;
import com.onedayfirm.gorodabot.handlers.HelpCommandHandler;
import com.onedayfirm.gorodabot.handlers.InfoCommandHandler;
import com.onedayfirm.gorodabot.handlers.StartGorodaGameCommandHandler;
import com.onedayfirm.gorodabot.mediawiki.MediaWiki;
import com.onedayfirm.gorodabot.search.SearchCachingProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("The bot is initializing...");
        try {
            var commandHandlers = List.of(
                    new HelpCommandHandler(),
                    new StartGorodaGameCommandHandler(KladrCitiesStorage.getInstance()),
                    new InfoCommandHandler(new SearchCachingProxy<>(new MediaWiki()))
            );
            var messageHandlers = List.of(new GorodaGameTurnHandler());
            var bot = new GorodaBot(commandHandlers, messageHandlers);
            LOGGER.info("Bot initialization completed");
            var client = new TelegramClient(bot);
            client.run();
        } catch (ExitException exception) {
            LOGGER.error("Emergency shutdown", exception.getCause());
            System.exit(exception.status);
        } catch (Error error) {
            LOGGER.error("Unexpected error", error);
        }
    }
}
