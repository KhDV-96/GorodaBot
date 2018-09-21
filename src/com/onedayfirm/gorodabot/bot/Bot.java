package com.onedayfirm.gorodabot.bot;

import java.util.List;

public interface Bot {

    boolean isUserConnected(int id);

    List<String> onConnection(int id);

    List<String> onMessage(int id, String message);
}
