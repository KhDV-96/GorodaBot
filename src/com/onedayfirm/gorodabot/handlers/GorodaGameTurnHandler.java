package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Phrases;
import com.onedayfirm.gorodabot.bot.Session;

import java.util.Collection;

public class GorodaGameTurnHandler extends MessageHandler {

    @Override
    boolean canHandle(String message, Session session) {
        return session.getGorodaGame() != null;
    }

    @Override
    void reply(String message, Session session, Collection<String> responses) {
        if (!session.getGorodaGame().isValidCity(message)) {
            responses.add(Phrases.getInstance().getPhrase("UNKNOWN CITY"));
        } else if (!session.getGorodaGame().isCorrectTurn(message)) {
            responses.add(Phrases.getInstance().getPhrase("WRONG ANSWER"));
        } else if (session.getGorodaGame().isCityUsed(message)) {
            responses.add(Phrases.getInstance().getPhrase("CITY ALREADY USED"));
        } else {
            var response = session.getGorodaGame().makeTurn(message);
            if (response == null) {
                response = Phrases.getInstance().getPhrase("LOSE");
                session.setGorodaGame(null);
            }
            responses.add(response);
        }
    }
}
