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
        responses.add(phrases.getPhrase("ABOUT"));
        responses.add(phrases.getPhrase("HELP"));
    }

    public void onMessage(int id, String message, Collection<String> responses) {
        var session = sessions.get(id);
        if (message.equals("/help")) {
            responses.add(phrases.getPhrase("ABOUT"));
            responses.add(phrases.getPhrase("HELP"));
        } else if (message.equals("/game")) {
            var game = new GorodaGame();
            session.setGorodaGame(game);
            responses.add(game.makeFirstTurn());
        } else if (session.getGorodaGame() != null) {
            handleGorodaGameTurn(session, message, responses);
        } else {
            responses.add(phrases.getPhrase("UNKNOWN COMMAND"));
            responses.add(phrases.getPhrase("HELP"));
        }
    }

    private void handleGorodaGameTurn(Session session, String message, Collection<String> responses) {
        if (!session.getGorodaGame().isValidCity(message)) {
            responses.add(phrases.getPhrase("UNKNOWN CITY"));
            return;
        }
        if (!session.getGorodaGame().isCorrectTurn(message)) {
            responses.add(phrases.getPhrase("WRONG ANSWER"));
            return;
        }
        if (session.getGorodaGame().isCityUsed(message)) {
            responses.add(phrases.getPhrase("CITY ALREADY USED"));
            return;
        }
        var city = session.getGorodaGame().makeTurn(message);
        if (city == null) {
            responses.add(phrases.getPhrase("LOSE"));
            session.setGorodaGame(null);
        } else
            responses.add(city);
    }
}
