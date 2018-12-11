package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.bot.Session;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramLongPollingClient extends TelegramLongPollingBot {

    private TelegramClient delegate;
    private String token;
    private String botName;

    TelegramLongPollingClient(TelegramClient main, String token, String botName){
        this.delegate = main;
        this.token = token;
        this.botName = botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        var text = message.getText();
        var id = message.getChatId();
        if (!delegate.isSessionExists(id)){
            delegate.handleConnection(new Session(id));
        } else {
            delegate.handleMessage(id, text);
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
        var sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText(message);
        try{
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
