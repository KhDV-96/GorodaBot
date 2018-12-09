package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;
import com.onedayfirm.gorodabot.goroda.GorodaGame;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GorodaGameTurnHandlerTest {

    @Test
    void canHandleTrue() {
        var handler = new GorodaGameTurnHandler();
        var session = new Session(0);
        session.setGorodaGame(new GorodaGame(null));

        assertTrue(handler.canHandle(null, session));
    }

    @Test
    void canHandleFalse() {
        var handler = new GorodaGameTurnHandler();
        var session = new Session(0);

        assertFalse(handler.canHandle(null, session));
    }

    @Test
    void handleInvalidCity() {
        var handler = new GorodaGameTurnHandler();
        var game = mock(GorodaGame.class);
        when(game.isValidCity(anyString())).thenReturn(false);
        var session = new Session(0);
        session.setGorodaGame(game);
        var responses = new ArrayList<String>();

        handler.handle("", session, responses);

        assertTrue(responses.size() > 0);
        verify(game, times(1)).isValidCity(anyString());
    }

    @Test
    void handleIncorrectTurn() {
        var handler = new GorodaGameTurnHandler();
        var game = mock(GorodaGame.class);
        when(game.isValidCity(anyString())).thenReturn(true);
        when(game.isCorrectTurn(anyString())).thenReturn(false);
        var session = new Session(0);
        session.setGorodaGame(game);
        var responses = new ArrayList<String>();

        handler.handle("", session, responses);

        assertTrue(responses.size() > 0);
        verify(game, times(1)).isCorrectTurn(anyString());
    }

    @Test
    void handleUsedCity() {
        var handler = new GorodaGameTurnHandler();
        var game = mock(GorodaGame.class);
        when(game.isValidCity(anyString())).thenReturn(true);
        when(game.isCorrectTurn(anyString())).thenReturn(true);
        when(game.isCityUsed(anyString())).thenReturn(true);
        var session = new Session(0);
        session.setGorodaGame(game);
        var responses = new ArrayList<String>();

        handler.handle("", session, responses);

        assertTrue(responses.size() > 0);
        verify(game, times(1)).isCityUsed(anyString());
    }

    @Test
    void handleNormalTurn() {
        var handler = new GorodaGameTurnHandler();
        var game = mock(GorodaGame.class);
        when(game.isValidCity(anyString())).thenReturn(true);
        when(game.isCorrectTurn(anyString())).thenReturn(true);
        when(game.isCityUsed(anyString())).thenReturn(false);
        when(game.makeTurn(anyString())).thenReturn("answer");
        var session = new Session(0);
        session.setGorodaGame(game);
        var responses = new ArrayList<String>();

        handler.handle("", session, responses);

        assertTrue(responses.size() > 0);
        assertNotNull(session.getGorodaGame());
    }

    @Test
    void handleLosingTurn() {
        var handler = new GorodaGameTurnHandler();
        var game = mock(GorodaGame.class);
        when(game.isValidCity(anyString())).thenReturn(true);
        when(game.isCorrectTurn(anyString())).thenReturn(true);
        when(game.isCityUsed(anyString())).thenReturn(false);
        var session = new Session(0);
        session.setGorodaGame(game);
        var responses = new ArrayList<String>();

        handler.handle("", session, responses);

        assertTrue(responses.size() > 0);
        assertNull(session.getGorodaGame());
    }
}
