package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.bot.Bot;
import com.onedayfirm.gorodabot.bot.Session;

import java.util.Scanner;

public class ConsoleClient extends Client {

    private final int static_id = hashCode();

    private Scanner scanner;

    public ConsoleClient(Bot bot) {
        super(bot);
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        handleConnection(new Session(static_id));
        while (!Thread.interrupted())
            handleMessage(static_id, scanner.nextLine());
    }

    @Override
    public void send(int id, String message) {
        System.out.println(message);
    }
}
