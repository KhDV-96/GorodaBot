package com.onedayfirm.gorodabot.goroda;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GorodaGameTest {

    private CitiesStorage storage = FileCitiesStorage.getInstance();

    @Test
    void makeTurnValid() {
        var game = new GorodaGame(storage);
        var firstCity = game.makeFirstTurn();
        var secondCity = game.makeTurn(firstCity);

        assertNotNull(firstCity);
        assertTrue(game.isCityUsed(secondCity));
    }

    @Test
    void makeTurnInvalid() {
        var game = new GorodaGame(storage);
        var city = game.makeTurn("foobar");

        assertNull(city);
    }

    @Test
    void isValidCityFalse() {
        var game = new GorodaGame(storage);
        var city = game.makeFirstTurn();

        assertTrue(game.isValidCity(city));
        assertTrue(game.isCityUsed(city));
    }

    @Test
    void isValidCityFalse2() {
        var game = new GorodaGame(storage);

        assertFalse(game.isValidCity("foobar"));
    }

    @Test
    void makeFirstTurn() {
        var game = new GorodaGame(storage);
        var city = game.makeFirstTurn();

        assertNotNull(city);
        assertTrue(game.isCityUsed(city));
    }

    @Test
    void isCityUsedTrue() {
        var game = new GorodaGame(storage);
        var message = game.makeFirstTurn();

        assertTrue(game.isCityUsed(message));
    }

    @Test
    void isCityUsedFalse() {
        var game = new GorodaGame(storage);

        assertFalse(game.isCityUsed("foobar"));
    }

    @Test
    void isValidCityUpperCase() {
        var game = new GorodaGame(storage);
        var city = game.makeFirstTurn();

        assertTrue(game.isValidCity(city));
    }

    @Test
    void isValidCityLowerCase() {
        var game = new GorodaGame(storage);
        var city = new StringBuilder(game.makeFirstTurn());
        city.setCharAt(0, Character.toLowerCase(city.charAt(0)));

        assertTrue(game.isValidCity(city.toString()));
    }

    @Test
    void isValidCityWrongCityENG() {
        var game = new GorodaGame(storage);
        var city = "foobar";

        assertFalse(game.isValidCity(city));
    }

    @Test
    void isValidCityWrongCityRUS() {
        var game = new GorodaGame(storage);
        var city = "Фуубар";

        assertFalse(game.isValidCity(city));
    }

    @Test
    void isCorrectTurnTrue() {
        var game1 = new GorodaGame(storage);
        var game2 = new GorodaGame(storage);

        var city1 = game1.makeFirstTurn();
        var city2 = game2.makeTurn(city1);
        var city3 = game1.makeTurn(city2);

        assertTrue(game2.isCorrectTurn(city3));
    }

    @Test
    void isCorrectTurnFalse() {
        var game1 = new GorodaGame(storage);
        game1.makeFirstTurn();

        assertFalse(game1.isCorrectTurn("city"));
    }

    @Test
    void addUsedCity() {
        var game1 = new GorodaGame(storage);
        var game2 = new GorodaGame(storage);
        var city = game1.makeFirstTurn();
        game2.makeTurn(city);

        assertTrue(game2.isCityUsed(city));
    }
}