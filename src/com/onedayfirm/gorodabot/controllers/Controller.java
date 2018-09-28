package com.onedayfirm.gorodabot.controllers;

public interface Controller {

    void handleConnection(int id);

    void handleMessage(int id, String message);
}
