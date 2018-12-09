package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InfoCommandHandlerTest {

    @Test
    void hasCommands() {
        var handler = new InfoCommandHandler();

        assertTrue(handler.getCommands().size() > 0);
    }

    @Test
    void handleNoGame() {
        var handler = new InfoCommandHandler();
        var session = new Session(0);
        var responses = new LinkedList<String>();

        handler.handle(null, session, responses);

        assertTrue(responses.size() > 0);
    }
}
