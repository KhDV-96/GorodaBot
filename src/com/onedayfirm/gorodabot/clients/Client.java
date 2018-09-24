package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.controllers.Controller;

public abstract class Client {

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public abstract void send(int id, String message);

    protected abstract void receive();

    void handleConnection(int id) {
        controller.handleConnection(this, id);
    }

    void handleMessage(int id, String text) {
        controller.handleMessage(this, id, text);
    }
}
