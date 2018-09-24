package com.onedayfirm.gorodabot.clients;

import java.util.Scanner;

public class TerminalClient extends Client {

    private final int id = hashCode();

    private Scanner scanner;

    public TerminalClient() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void receive() {
        handleConnection(id);
        while (true)
            handleMessage(id, scanner.nextLine());
    }

    @Override
    public void send(int id, String message) {
        System.out.println(message);
    }
}
