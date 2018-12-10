package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;
import com.onedayfirm.gorodabot.goroda.CitiesStorage;
import com.onedayfirm.gorodabot.goroda.GorodaGame;

import java.util.Collection;

public class StartGorodaGameCommandHandler extends CommandHandler {

    private CitiesStorage storage;

    public StartGorodaGameCommandHandler(CitiesStorage storage) {
        super("GAME");
        this.storage = storage;
    }

    @Override
    public void handle(String message, Session session, Collection<String> responses) {
        var game = new GorodaGame(storage);
        session.setGorodaGame(game);
        responses.add(game.makeFirstTurn());
    }
}
