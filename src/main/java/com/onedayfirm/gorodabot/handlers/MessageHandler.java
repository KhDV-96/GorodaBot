package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;

public interface MessageHandler extends Handler {

    boolean canHandle(String message, Session session);
}
