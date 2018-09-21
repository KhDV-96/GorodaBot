package com.onedayfirm.gorodabot.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bot {

    private HashMap<Integer, Session> sessions;
    private Phrases phrases = Phrases.getInstance();

    public Bot() {
        sessions = new HashMap<>();
    }

    public List<String> onConnection(int id) {
        sessions.put(id, new Session());
        var response = new ArrayList<String>();
        response.add(phrases.getPhrase("GREETING"));
        response.add(phrases.getPhrase("HELP"));
        return response;
    }

    public List<String> onMessage(int id, String message) {
        var response = new ArrayList<String>();
        response.add(String.format("[%d] Message: %s", id, message));
        return response;
    }
}
