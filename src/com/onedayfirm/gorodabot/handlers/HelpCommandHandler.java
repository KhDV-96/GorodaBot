package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Phrases;
import com.onedayfirm.gorodabot.bot.Session;

import java.util.Collection;

public class HelpCommandHandler extends CommandHandler {

    private static final String COMMAND = "/help";

    public HelpCommandHandler() {
        super(COMMAND);
    }

    @Override
    void reply(String message, Session session, Collection<String> responses) {
        responses.add(Phrases.getInstance().getPhrase("ABOUT"));
        responses.add(Phrases.getInstance().getPhrase("HELP"));
    }
}
