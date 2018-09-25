package com.onedayfirm.gorodabot.goroda;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GorodaGameTest {

    @Test
    void makeTurnValid() {
        var game = new GorodaGame();
        var firstCity = game.makeFirstTurn();
        var secondCity = game.makeTurn(firstCity);

        assertNotNull(firstCity);
        assertEquals(firstCity.charAt(firstCity.length() - 1), secondCity.charAt(0));
        assertTrue(game.isCityUsed(secondCity));
    }

    @Test
    void makeTurnInvalid() {
        var game = new GorodaGame();
        var city = game.makeTurn("foobar");

        assertNull(city);
    }

    @Test
    void isValidCityFalse() {
        var game = new GorodaGame();
        var city = game.makeFirstTurn();

        assertFalse(game.isValidCity(city));
        assertTrue(game.isCityUsed(city));
    }

    @Test
    void isValidCityFalse2() {
        var game = new GorodaGame();

        assertFalse(game.isValidCity("foobar"));
    }

    @Test
    void makeFirstTurn() {
        var game = new GorodaGame();
        var city = game.makeFirstTurn();

        assertNotNull(city);
        assertTrue(game.isCityUsed(city));
    }

    @Test
    void isCityUsedTrue() {
        var game = new GorodaGame();
        var message = game.makeFirstTurn();

        assertTrue(game.isCityUsed(message));
    }

    @Test
    void isCityUsedFalse() {
        var game = new GorodaGame();

        assertFalse(game.isCityUsed("foobar"));
    }
}