package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;
import com.onedayfirm.gorodabot.containers.Phrases;
import com.onedayfirm.gorodabot.mediawiki.MediaWiki;
import com.onedayfirm.gorodabot.utils.Configurations;

import java.util.Collection;

public class InfoCommandHandler extends CommandHandler {

    private static final String KEY_WORD = Configurations.getProperty("infoCommandHandler.keyWord");

    private MediaWiki wiki;

    public InfoCommandHandler(MediaWiki wiki) {
        super("INFO");
        this.wiki = wiki;
    }

    @Override
    public void handle(String message, Session session, Collection<String> responses) {
        var game = session.getGorodaGame();
        if (game == null || game.getPreviousCity() == null) {
            responses.add(Phrases.getInstance().get("NO PREVIOUS CITY"));
            return;
        }
        var response = wiki.search(game.getPreviousCity(), KEY_WORD);
        if (response == null)
            response = Phrases.getInstance().get("NO INFO");
        responses.add(response);
    }
}
