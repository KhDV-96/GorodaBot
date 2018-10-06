package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;
import com.onedayfirm.gorodabot.goroda.GorodaGame;

import java.util.Collection;

public class StartGorodaGameCommandHandler extends CommandHandler {

    private static final String COMMAND = "/game";

    public StartGorodaGameCommandHandler() {
        super(COMMAND);
    }

    @Override
    void reply(String message, Session session, Collection<String> responses) {
        var game = new GorodaGame();
        session.setGorodaGame(game);
        responses.add(game.makeFirstTurn());
    }
}
