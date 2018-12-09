package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;
import com.onedayfirm.gorodabot.goroda.CitiesStorage;
import com.onedayfirm.gorodabot.goroda.City;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StartGorodaGameCommandHandlerTest {

    @Test
    void hasCommands() {
        var handler = new StartGorodaGameCommandHandler(null);

        assertTrue(handler.getCommands().size() > 0);
    }

    @Test
    void handleCreatesNewGame() {
        var city = mock(City.class);
        when(city.getName()).thenReturn("City");
        var storage = mock(CitiesStorage.class);
        when(storage.getAvailableLetters()).thenReturn(Set.of('a'));
        when(storage.getCitiesByLetter(anyChar())).thenReturn(List.of(city));
        var handler = new StartGorodaGameCommandHandler(storage);
        var session = new Session(0);
        var responses = new LinkedList<String>();

        handler.handle(null, session, responses);

        assertNotNull(session.getGorodaGame());
        assertTrue(responses.size() > 0);
        assertEquals(city.getName(), responses.get(0));
    }
}
