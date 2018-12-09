package com.onedayfirm.gorodabot.bot;

import com.onedayfirm.gorodabot.handlers.CommandHandler;
import com.onedayfirm.gorodabot.handlers.MessageHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GorodaBotTest {

    @Test
    void isUserConnectedTrue() {
        var bot = new GorodaBot(new ArrayList<>(), new ArrayList<>());
        var id = 1;
        bot.onConnection(new Session(id), new ArrayList<>());

        assertTrue(bot.isUserConnected(id));
    }

    @Test
    void isUserConnectedFalse() {
        var bot = new GorodaBot(new ArrayList<>(), new ArrayList<>());
        bot.onConnection(new Session(0), new ArrayList<>());

        assertFalse(bot.isUserConnected(1));
    }

    @Test
    void getUserSessionCorrect() {
        var bot = new GorodaBot(new ArrayList<>(), new ArrayList<>());
        var id = 1;
        var session = new Session(id);
        bot.onConnection(session, new ArrayList<>());

        assertNotNull(bot.getUserSession(id));
        assertEquals(session, bot.getUserSession(id));
    }

    @Test
    void getUserSessionIncorrect() {
        var bot = new GorodaBot(new ArrayList<>(), new ArrayList<>());
        bot.onConnection(new Session(0), new ArrayList<>());

        assertNull(bot.getUserSession(1));
    }

    @Test
    void onConnectionSessionSaved() {
        var bot = new GorodaBot(new ArrayList<>(), new ArrayList<>());
        var id = 1;
        var session = new Session(id);
        var responses = new ArrayList<String>();

        bot.onConnection(session, responses);

        assertTrue(bot.isUserConnected(id));
        assertNotNull(bot.getUserSession(id));
        assertEquals(session, bot.getUserSession(id));
    }

    @Test
    void onConnectionAnyResponse() {
        var bot = new GorodaBot(new ArrayList<>(), new ArrayList<>());
        var id = 1;
        var session = new Session(id);
        var responses = new ArrayList<String>();

        bot.onConnection(session, responses);

        assertTrue(responses.size() > 0);
    }

    @Test
    void onMessageWhenMessage() {
        var handler = mock(MessageHandler.class, CALLS_REAL_METHODS);
        when(handler.canHandle(anyString(), any())).thenReturn(true);
        doAnswer(invocation -> {
            ((Collection<String>) invocation.getArgument(2)).add("answer");
            return null;
        }).when(handler).handle(anyString(), any(), anyCollection());
        var bot = new GorodaBot(new ArrayList<>(), List.of(handler));
        var id = 1;
        var responses = new ArrayList<String>();
        bot.onConnection(new Session(id), new ArrayList<>());

        bot.onMessage(id, "test", responses);

        assertIterableEquals(List.of("answer"), responses);
    }

    @Test
    void onMessageWhenCommand() {
        var handler = mock(CommandHandler.class, CALLS_REAL_METHODS);
        when(handler.getCommands()).thenReturn(List.of("/test"));
        doAnswer(invocation -> {
            ((Collection<String>) invocation.getArgument(2)).add("answer");
            return null;
        }).when(handler).handle(anyString(), any(), anyCollection());
        var bot = new GorodaBot(List.of(handler), new ArrayList<>());
        var id = 1;
        var responses = new ArrayList<String>();
        bot.onConnection(new Session(id), new ArrayList<>());

        bot.onMessage(id, "/test", responses);

        assertIterableEquals(List.of("answer"), responses);
    }

    @Test
    void onMessageWhenUnhandledMessage() {
        var handler = mock(MessageHandler.class, CALLS_REAL_METHODS);
        when(handler.canHandle(anyString(), any())).thenReturn(false);
        var bot = new GorodaBot(new ArrayList<>(), List.of(handler));
        var id = 1;
        var responses = new ArrayList<String>();
        bot.onConnection(new Session(id), new ArrayList<>());

        bot.onMessage(id, "unhandled", responses);

        assertTrue(responses.size() > 0);
    }
}
