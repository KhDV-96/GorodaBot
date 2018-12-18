package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.bot.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramLongPollingClient extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramLongPollingClient.class);

    private Client delegate;
    private String token;
    private String botName;

    TelegramLongPollingClient(Client main, String token, String botName) {
        delegate = main;
        this.token = token;
        this.botName = botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        var text = message.getText();
        var id = message.getChatId();
        if (delegate.bot.isUserConnected(id)) {
            LOGGER.info("Received: {id={}, name={}, text='{}'}", id, message.getFrom().getUserName(), text);
            delegate.handleMessage(id, text);
        } else {
            LOGGER.info("User connected {id={}, name={}}", id, message.getFrom().getUserName());
            delegate.handleConnection(new Session(id));
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    void sendMessage(long id, String message) {
        var sendMessage = new SendMessage()
                .setChatId(id)
                .setText(message);
        try {
            execute(sendMessage);
            LOGGER.info("Sent: {id={}, text='{}'}", id, message);
        } catch (TelegramApiException exception) {
            LOGGER.error("TelegramLongPollingClient bot could not send the message", exception);
        }
    }
}
