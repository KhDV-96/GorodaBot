package com.onedayfirm.gorodabot.goroda;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GorodaGameTest {

    @Test
    void makeTurn() {
        var game = new GorodaGame();
        var firstTurn = game.makeFirstTurn();
        var secondTurn = game.makeTurn(firstTurn);
        System.out.println(secondTurn);
        assertNotNull(secondTurn);
    }

    @Test
    void isValidCity() {
        var game = new GorodaGame();
        assertTrue(game.isValidCity("астрахань"));
    }

    @Test
    void makeFirstTurn() {
        var game = new GorodaGame();
        var message = game.makeFirstTurn();
        assertNotNull(message);
    }

    @Test
    void isCityUsed() {
        var game = new GorodaGame();
        var message = game.makeFirstTurn();
        assertTrue(game.isCityUsed(message));
        assertFalse(game.isCityUsed("астрахань"));
    }
}