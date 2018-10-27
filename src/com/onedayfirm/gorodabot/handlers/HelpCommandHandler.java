package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;
import com.onedayfirm.gorodabot.containers.Commands;
import com.onedayfirm.gorodabot.containers.Phrases;

import java.util.Collection;

public class HelpCommandHandler extends CommandHandler {

    private Phrases phrases = Phrases.getInstance();

    public HelpCommandHandler() {
        Commands.getInstance().get("HELP").forEach(this::addCommand);
    }

    @Override
    public void handle(String message, Session session, Collection<String> responses) {
        responses.add(phrases.get("ABOUT"));
        responses.add(phrases.get("HELP"));
    }
}
