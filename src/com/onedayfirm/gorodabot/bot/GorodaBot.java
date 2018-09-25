package com.onedayfirm.gorodabot.bot;

import com.onedayfirm.gorodabot.goroda.GorodaGame;

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
        if (message.equals("/game")) {
            var game = new GorodaGame();
            sessions.get(id).setGorodaGame(game);
            responses.add(game.makeFirstTurn());
        } else if (sessions.get(id).getGorodaGame() != null) {
            if (sessions.get(id).getGorodaGame().isValidCity(message))
                responses.add(sessions.get(id).getGorodaGame().makeTurn(message));
            else
                responses.add(phrases.getPhrase("UNKNOWN CITY"));
        } else {
            responses.add(phrases.getPhrase("UNKNOWN COMMAND"));
        }
    }
}
