package com.onedayfirm.gorodabot.bot;

import com.onedayfirm.gorodabot.containers.Phrases;
import com.onedayfirm.gorodabot.handlers.CommandHandler;
import com.onedayfirm.gorodabot.handlers.Handler;
import com.onedayfirm.gorodabot.handlers.MessageHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GorodaBot implements Bot {

    private Phrases phrases = Phrases.getInstance();
    private Map<Long, Session> sessions;
    private Map<String, Handler> commandHandlers;
    private Collection<? extends MessageHandler> messageHandlers;

    public GorodaBot(Collection<? extends CommandHandler> commandHandlers,
                     Collection<? extends MessageHandler> messageHandlers) {
        sessions = new HashMap<>();
        this.messageHandlers = messageHandlers;
        this.commandHandlers = new HashMap<>();
        saveCommandHandlers(commandHandlers);
    }

    public boolean isUserConnected(long id) {
        return sessions.containsKey(id);
    }

    public Session getUserSession(long id) {
        return sessions.get(id);
    }

    public void onConnection(Session session, Collection<String> responses) {
        sessions.put(session.getId(), session);
        responses.add(phrases.get("GREETING"));
        responses.add(phrases.get("ABOUT"));
        responses.add(phrases.get("HELP"));
    }

    public void onMessage(long id, String message, Collection<String> responses) {
        var session = sessions.get(id);
        var handler = getHandler(message, session);
        if (handler == null)
            onUnhandledMessage(responses);
        else
            handler.handle(message, session, responses);
    }

    private Handler getHandler(String message, Session session) {
        if (CommandHandler.isCommand(message))
            return commandHandlers.get(message);
        for (var messageHandler : messageHandlers) {
            if (messageHandler.canHandle(message, session))
                return messageHandler;
        }
        return null;
    }

    private void onUnhandledMessage(Collection<String> responses) {
        responses.add(phrases.get("UNKNOWN COMMAND"));
        responses.add(phrases.get("HELP"));
    }

    private void saveCommandHandlers(Collection<? extends CommandHandler> handlers) {
        for (var handler : handlers) {
            for (var command : handler.getCommands())
                commandHandlers.put(command, handler);
        }
    }
}
