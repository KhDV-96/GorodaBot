package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;
import com.onedayfirm.gorodabot.containers.Phrases;
import com.onedayfirm.gorodabot.goroda.GorodaGame;

import java.util.Collection;

public class GorodaGameTurnHandler implements MessageHandler {

    private Phrases phrases = Phrases.getInstance();

    @Override
    public boolean canHandle(String message, Session session) {
        return session.getGorodaGame() != null;
    }

    @Override
    public void handle(String message, Session session, Collection<String> responses) {
        if (checkTurn(message, session.getGorodaGame(), responses)) {
            var response = session.getGorodaGame().makeTurn(message);
            if (response == null) {
                response = phrases.get("LOSE");
                session.setGorodaGame(null);
            }
            responses.add(response);
        }
    }

    private boolean checkTurn(String message, GorodaGame game, Collection<String> responses) {
        if (!game.isValidCity(message)) {
            responses.add(phrases.get("UNKNOWN CITY"));
            return false;
        }
        if (!game.isCorrectTurn(message)) {
            responses.add(phrases.get("WRONG ANSWER"));
            return false;
        }
        if (game.isCityUsed(message)) {
            responses.add(phrases.get("CITY ALREADY USED"));
            return false;
        }
        return true;
    }
}
