package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.bot.Bot;
import com.onedayfirm.gorodabot.bot.Session;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.mockito.Mockito.*;

class ClientTest {

    @Test
    void handleConnectionNoResponse() {
        var bot = mock(Bot.class);
        var client = spy(new PrimitiveClient(bot));
        client.handleConnection(new Session(0));

        verify(bot, atLeastOnce()).onConnection(any(Session.class), anyCollection());
        verify(client, never()).send(anyInt(), anyString());
    }

    @Test
    @SuppressWarnings("unchecked")
    void handleConnectionManyResponses() {
        var bot = mock(Bot.class);
        var client = spy(new PrimitiveClient(bot));
        final var n = 3;
        doAnswer(invocation -> {
            var responses = ((Collection<String>) invocation.getArgument(1));
            for (var i = 0; i < n; i++)
                responses.add("");
            return null;
        }).when(bot).onConnection(any(Session.class), anyCollection());

        client.handleConnection(new Session(0));

        verify(bot, atLeastOnce()).onConnection(any(Session.class), anyCollection());
        verify(client, times(n)).send(anyLong(), anyString());
    }

    @Test
    void handleMessageEmptyMessage() {
        var bot = mock(Bot.class);
        var client = spy(new PrimitiveClient(bot));

        client.handleMessage(0, "");

        verify(bot, never()).onMessage(anyInt(), anyString(), anyCollection());
        verify(client, never()).send(anyInt(), anyString());
    }

    @Test
    void handleMessageNoResponses() {
        var bot = mock(Bot.class);
        var client = spy(new PrimitiveClient(bot));

        client.handleMessage(0, "message");

        verify(bot, atLeastOnce()).onMessage(anyLong(), anyString(), anyCollection());
        verify(client, never()).send(anyLong(), anyString());
    }

    @Test
    @SuppressWarnings("unchecked")
    void handleMessageManyResponses() {
        var bot = mock(Bot.class);
        var client = spy(new PrimitiveClient(bot));
        final var n = 3;
        doAnswer(invocation -> {
            var responses = ((Collection<String>) invocation.getArgument(2));
            for (var i = 0; i < n; i++)
                responses.add("");
            return null;
        }).when(bot).onMessage(anyLong(), anyString(), anyCollection());

        client.handleMessage(0, "message");

        verify(bot, atLeastOnce()).onMessage(anyLong(), anyString(), anyCollection());
        verify(client, times(n)).send(anyLong(), anyString());
    }

    static class PrimitiveClient extends Client {

        PrimitiveClient(Bot bot) {
            super(bot);
        }

        @Override
        public void run() {
            // pass
        }

        @Override
        void send(long id, String message) {
            // pass
        }
    }
}
