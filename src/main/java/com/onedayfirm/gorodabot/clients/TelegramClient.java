package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.ExitException;
import com.onedayfirm.gorodabot.bot.Bot;
import com.onedayfirm.gorodabot.utils.Configurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramClient extends Client {

    private static final String TOKEN = Configurations.getProperty("token");
    private static final String BOT_NAME = Configurations.getProperty("telegramClient.botName");
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramClient.class);

    private TelegramLongPollingClient client;

    public TelegramClient(Bot bot) {
        super(bot);
    }

    @Override
    public void run() {
        LOGGER.info("Telegram client is starting...");
        ApiContextInitializer.init();
        var api = new TelegramBotsApi();
        client = new TelegramLongPollingClient(this, TOKEN, BOT_NAME);
        try {
            api.registerBot(client);
            LOGGER.info("Telegram client has started");
        } catch (TelegramApiException exception) {
            LOGGER.error("Failed to start the telegram client");
            throw new ExitException(exception, 2);
        }
    }

    @Override
    void send(long id, String message) {
        client.sendMessage(id, message);
    }
}
