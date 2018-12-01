package com.onedayfirm.gorodabot.bot;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
    void onMessageAnyResponse() {
        var bot = new GorodaBot(new ArrayList<>(), new ArrayList<>());
        var id = 1;
        var responses = new ArrayList<String>();
        bot.onConnection(new Session(id), new ArrayList<>());

        bot.onMessage(id, "test", responses);

        assertTrue(responses.size() > 0);
    }

    @Test
    void onMessageAsCommandAnyResponse() {
        var bot = new GorodaBot(new ArrayList<>(), new ArrayList<>());
        var id = 1;
        var responses = new ArrayList<String>();
        bot.onConnection(new Session(id), new ArrayList<>());

        bot.onMessage(id, "/test", responses);

        assertTrue(responses.size() > 0);
    }
}
