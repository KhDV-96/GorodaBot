package com.onedayfirm.gorodabot.bot;

import com.onedayfirm.gorodabot.handlers.Handler;

import java.util.Collection;
import java.util.HashMap;

import static com.onedayfirm.gorodabot.handlers.CommandHandler.isCommand;

public class GorodaBot implements Bot {

    private HashMap<Integer, Session> sessions;
    private Phrases phrases = Phrases.getInstance();
    private Handler commandHandler;
    private Handler messageHandler;

    public GorodaBot(Handler commandHandler, Handler messageHandler) {
        this.commandHandler = commandHandler;
        this.messageHandler = messageHandler;
        sessions = new HashMap<>();
    }

    public boolean isUserConnected(int id) {
        return sessions.containsKey(id);
    }

    public void onConnection(int id, Collection<String> responses) {
        sessions.put(id, new Session());
        responses.add(phrases.getPhrase("GREETING"));
        responses.add(phrases.getPhrase("ABOUT"));
        responses.add(phrases.getPhrase("HELP"));
    }

    public void onMessage(int id, String message, Collection<String> responses) {
        var session = sessions.get(id);
        var handler = isCommand(message) ? commandHandler : messageHandler;
        handler.handle(message, session, responses);
    }
}
