package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.bot.Bot;

import java.util.Scanner;

public class ConsoleClient extends Client {

    private final int id = hashCode();

    private Scanner scanner;

    public ConsoleClient(Bot bot) {
        super(bot);
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        handleConnection(id);
        while (!Thread.interrupted())
            handleMessage(id, scanner.nextLine());
    }

    @Override
    public void send(int id, String message) {
        System.out.println(message);
    }
}
