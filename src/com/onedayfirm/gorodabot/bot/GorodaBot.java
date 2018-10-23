package com.onedayfirm.gorodabot.bot;

import com.onedayfirm.gorodabot.containers.Phrases;
import com.onedayfirm.gorodabot.handlers.CommandHandler;
import com.onedayfirm.gorodabot.handlers.Handler;
import com.onedayfirm.gorodabot.handlers.MessageHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GorodaBot implements Bot {

    private HashMap<Integer, Session> sessions;
    private Phrases phrases = Phrases.getInstance();
    private Map<String, Handler> commandHandlers;
    private Collection<MessageHandler> messageHandlers;

    public GorodaBot(Collection<CommandHandler> commandHandlers, Collection<MessageHandler> messageHandlers) {
        this.commandHandlers = new HashMap<>();
        this.messageHandlers = messageHandlers;
        sessions = new HashMap<>();
        saveCommandHandlers(commandHandlers);
    }

    public boolean isUserConnected(int id) {
        return sessions.containsKey(id);
    }

    public void onConnection(int id, Collection<String> responses) {
        sessions.put(id, new Session());
        responses.add(phrases.getPhrase("GREETING"));
        responses.add(phrases.getPhrase("ABOUT"));
        responses.add(phrases.getPhrase("HELP"));
    }

    public void onMessage(int id, String message, Collection<String> responses) {
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
        responses.add(phrases.getPhrase("UNKNOWN COMMAND"));
        responses.add(phrases.getPhrase("HELP"));
    }

    private void saveCommandHandlers(Collection<CommandHandler> handlers) {
        for (var handler : handlers) {
            for (var command : handler.getCommands())
                commandHandlers.put(command, handler);
        }
    }
}
