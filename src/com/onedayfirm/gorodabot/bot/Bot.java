package com.onedayfirm.gorodabot.bot;

import java.util.Collection;

public interface Bot {

    boolean isUserConnected(int id);

    void onConnection(Session session, Collection<String> responses);

    void onMessage(int id, String message, Collection<String> responses);
}
