package com.onedayfirm.gorodabot.handlers;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HelpCommandHandlerTest {

    @Test
    void hasCommands() {
        var handler = new HelpCommandHandler();

        assertTrue(handler.getCommands().size() > 0);
    }

    @Test
    void handleReturnText() {
        var handler = new HelpCommandHandler();
        var responses = new LinkedList<String>();

        handler.handle(null, null, responses);

        assertTrue(responses.size() > 0);
    }
}
