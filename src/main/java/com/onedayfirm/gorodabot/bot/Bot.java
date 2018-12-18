package com.onedayfirm.gorodabot.bot;

import java.util.Collection;

public interface Bot {

    boolean isUserConnected(long id);

    Session getUserSession(long id);

    void onConnection(Session session, Collection<String> responses);

    void onMessage(long id, String message, Collection<String> responses);
}
