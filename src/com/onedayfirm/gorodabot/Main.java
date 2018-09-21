package com.onedayfirm.gorodabot;

import com.onedayfirm.gorodabot.bot.Bot;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var bot = new Bot();
        bot.onConnection(0).forEach(System.out::println);
        while (true)
            bot.onMessage(0, scanner.nextLine()).forEach(System.out::println);
    }
}
