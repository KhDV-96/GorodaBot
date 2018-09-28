package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.controllers.Controller;

public abstract class Client implements Runnable {

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public abstract void send(int id, String message);

    public abstract void run();

    void handleConnection(int id) {
        controller.handleConnection(id);
    }

    void handleMessage(int id, String text) {
        controller.handleMessage(id, text);
    }
}
