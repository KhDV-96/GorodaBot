package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;
import com.onedayfirm.gorodabot.containers.Commands;
import com.onedayfirm.gorodabot.goroda.GorodaGame;

import java.util.Collection;

public class StartGorodaGameCommandHandler extends CommandHandler {

    public StartGorodaGameCommandHandler() {
        Commands.getInstance().get("GAME").forEach(this::addCommand);
    }

    @Override
    public void handle(String message, Session session, Collection<String> responses) {
        var game = new GorodaGame();
        session.setGorodaGame(game);
        responses.add(game.makeFirstTurn());
    }
}
