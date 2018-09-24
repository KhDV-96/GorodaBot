package com.onedayfirm.gorodabot.controllers;

import com.onedayfirm.gorodabot.clients.Client;

public interface Controller {

    void handleConnection(Client client, int id);

    void handleMessage(Client client, int id, String message);
}
