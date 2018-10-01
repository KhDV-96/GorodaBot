package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.controllers.Controller;

public abstract class Client implements Runnable {

    private Controller controller;

    Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public abstract void send(int id, String message);

    public abstract void run();
}
