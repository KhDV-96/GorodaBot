package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Phrases;
import com.onedayfirm.gorodabot.bot.Session;

import java.util.Collection;

public class HelpCommandHandler extends CommandHandler {

    private Phrases phrases = Phrases.getInstance();

    public HelpCommandHandler() {
        addCommand("/help");
    }

    @Override
    public void handle(String message, Session session, Collection<String> responses) {
        responses.add(phrases.getPhrase("ABOUT"));
        responses.add(phrases.getPhrase("HELP"));
    }
}
