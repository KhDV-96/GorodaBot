package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.bot.Bot;
import com.onedayfirm.gorodabot.utils.Configurations;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramClient extends Client {

    private static final String TOKEN = Configurations.getProperty("token");
    private static final String BOT_NAME = Configurations.getProperty("telegramClient.botName");

    private TelegramLongPollingClient client;

    public TelegramClient(Bot bot) {
        super(bot);
    }

    @Override
    public void run() {
        ApiContextInitializer.init();
        var api = new TelegramBotsApi();
        client = new TelegramLongPollingClient(this, TOKEN, BOT_NAME);
        try {
            api.registerBot(client);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    void send(long id, String message) {
        client.sendMessage(id, message);
    }
}
