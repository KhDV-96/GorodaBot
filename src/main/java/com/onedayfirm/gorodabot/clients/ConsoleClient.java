package com.onedayfirm.gorodabot.clients;

import com.onedayfirm.gorodabot.bot.Bot;
import com.onedayfirm.gorodabot.bot.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ConsoleClient extends Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleClient.class);

    private final int staticId = hashCode();

    private Scanner scanner;

    public ConsoleClient(Bot bot) {
        super(bot);
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        LOGGER.info("Console client started (static id = {})", staticId);
        handleConnection(new Session(staticId));
        while (!Thread.interrupted()) {
            var message = scanner.nextLine();
            LOGGER.info("Received message: {id={}, message='{}'}", staticId, message);
            handleMessage(staticId, message);
        }
    }

    @Override
    public void send(long id, String message) {
        System.out.println(message);
    }
}
