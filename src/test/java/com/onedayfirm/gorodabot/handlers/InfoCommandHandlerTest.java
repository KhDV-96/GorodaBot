package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;
import com.onedayfirm.gorodabot.goroda.GorodaGame;
import com.onedayfirm.gorodabot.mediawiki.MediaWiki;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class InfoCommandHandlerTest {

    @Test
    void hasCommands() {
        var handler = new InfoCommandHandler(null);

        assertTrue(handler.getCommands().size() > 0);
    }

    @Test
    void handleNoGame() {
        var handler = new InfoCommandHandler(null);
        var session = new Session(0);
        var responses = new LinkedList<String>();

        handler.handle(null, session, responses);

        assertTrue(responses.size() > 0);
    }

    @Test
    void handleHasInformation() {
        var info = "info";
        var wiki = mock(MediaWiki.class);
        when(wiki.search(anyString())).thenReturn(info);
        var handler = new InfoCommandHandler(wiki);
        var game = mock(GorodaGame.class);
        when(game.getPreviousCity()).thenReturn("City");
        var session = new Session(0);
        session.setGorodaGame(game);
        var responses = new LinkedList<String>();

        handler.handle(null, session, responses);

        assertTrue(responses.size() > 0);
        assertIterableEquals(List.of(info), responses);
        verify(wiki, times(1)).search(anyString());
    }

    @Test
    void handleNoInformation() {
        var wiki = mock(MediaWiki.class);
        var handler = new InfoCommandHandler(wiki);
        var game = mock(GorodaGame.class);
        when(game.getPreviousCity()).thenReturn("City");
        var session = new Session(0);
        session.setGorodaGame(game);
        var responses = new LinkedList<String>();

        handler.handle(null, session, responses);

        assertTrue(responses.size() > 0);
        verify(wiki, times(1)).search(anyString());
    }
}
