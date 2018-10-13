package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Phrases;
import com.onedayfirm.gorodabot.bot.Session;

import java.util.Collection;

public class HelpCommandHandler extends CommandHandler {

    public HelpCommandHandler() {
        addCommand("/help");
    }

    @Override
    public void handle(String message, Session session, Collection<String> responses) {
        responses.add(Phrases.getInstance().getPhrase("ABOUT"));
        responses.add(Phrases.getInstance().getPhrase("HELP"));
    }
}
