package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.bot.Bot;
import com.onedayfirm.gorodabot.bot.Session;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ConsoleClientTest {

    @Test
    void connectAtStart() throws Exception {
        var in = mock(InputStream.class);
        when(in.read()).thenThrow(AssertionError.class);
        System.setIn(in);
        var bot = mock(Bot.class);
        var client = new ConsoleClient(bot);

        assertThrows(Exception.class, client::run);
        verify(bot, times(1))
                .onConnection(any(Session.class), anyCollection());
    }

    @Test
    void sendCorrect() {
        var out = mock(PrintStream.class);
        System.setOut(out);
        var bot = mock(Bot.class);
        var client = new ConsoleClient(bot);

        client.send(0, "message");

        assertThrows(Exception.class, client::run);
        verify(out, times(1)).println(anyString());
    }
}
