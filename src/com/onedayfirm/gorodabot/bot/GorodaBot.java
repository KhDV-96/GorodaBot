package com.onedayfirm.gorodabot.bot;

import java.util.Collection;
import java.util.HashMap;

public class GorodaBot implements Bot {

    private HashMap<Integer, Session> sessions;
    private Phrases phrases = Phrases.getInstance();

    public GorodaBot() {
        sessions = new HashMap<>();
    }
    
    public boolean isUserConnected(int id) {
        return sessions.containsKey(id);
    }

    public void onConnection(int id, Collection<String> responses) {
        sessions.put(id, new Session());
        responses.add(phrases.getPhrase("GREETING"));
        responses.add(phrases.getPhrase("HELP"));
    }

    public void onMessage(int id, String message, Collection<String> responses) {
        responses.add(String.format("[%d] Message: %s", id, message));
    }
}
