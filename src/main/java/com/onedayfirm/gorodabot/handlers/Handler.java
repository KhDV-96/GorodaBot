package com.onedayfirm.gorodabot.handlers;

import com.onedayfirm.gorodabot.bot.Session;

import java.util.Collection;

public interface Handler {

    void handle(String message, Session session, Collection<String> responses);
}
