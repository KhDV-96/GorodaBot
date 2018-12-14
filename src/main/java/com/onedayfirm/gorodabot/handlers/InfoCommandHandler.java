package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;
import com.onedayfirm.gorodabot.containers.Phrases;
import com.onedayfirm.gorodabot.search.SearchService;

import java.util.Collection;

public class InfoCommandHandler extends CommandHandler {

    private SearchService<String, String> searchService;

    public InfoCommandHandler(SearchService<String, String> service) {
        super("INFO");
        searchService = service;
    }

    @Override
    public void handle(String message, Session session, Collection<String> responses) {
        var game = session.getGorodaGame();
        if (game == null || game.getPreviousCity() == null) {
            responses.add(Phrases.getInstance().get("NO PREVIOUS CITY"));
            return;
        }
        var response = searchService.search(game.getPreviousCity());
        if (response == null)
            response = Phrases.getInstance().get("NO INFO");
        responses.add(response);
    }
}
