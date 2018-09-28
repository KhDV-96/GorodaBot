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
        assertTrue(game.isCityUsed(secondCity));
    }

    @Test
    void makeTurnInvalid() {
        var game = new GorodaGame();
        var city = game.makeTurn("foobar");

        assertNull(city);
    }

    @Test
    void getLastCharOfCity() {
        var game = new GorodaGame();
        var storage = FileCitiesStorage.getInstance();
        var availableLetters = storage.getAvailableLetters();
        String exceptionCity = "";
        loop:
        for (var letter : availableLetters) {
            for (var city : storage.getCitiesByLetter(letter)) {
                if (!availableLetters.contains(city.charAt(city.length() - 1))) {
                    exceptionCity = city;
                    break loop;
                }
            }
        }
        var city = game.makeTurn(exceptionCity).toLowerCase();

        assertEquals(exceptionCity.charAt(exceptionCity.length() - 2), city.charAt(0));
    }

    @Test
    void isValidCityFalse() {
        var game = new GorodaGame();
        var city = game.makeFirstTurn();

        assertTrue(game.isValidCity(city));
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

    @Test
    void isValidCityUpperCase() {
        var game = new GorodaGame();
        var city = "Назарет";

        assertTrue(game.isValidCity(city));
    }

    @Test
    void isValidCityLowerCase() {
        var game = new GorodaGame();
        var city = "сочи";

        assertTrue(game.isValidCity(city));
    }

    @Test
    void isValidCityAfterFirstTurn() {
        var game = new GorodaGame();

        assertTrue(game.isValidCity("сочи"));
    }

    @Test
    void isValidCityWrongCityENG() {
        var game = new GorodaGame();
        var city = "foobar";

        assertFalse(game.isValidCity(city));
    }

    @Test
    void isValidCityWrondCityRUS() {
        var game = new GorodaGame();
        var city = "Фуубар";

        assertFalse(game.isValidCity(city));
    }

    @Test
    void isCorrectTurnTrue() {
        var game1 = new GorodaGame();
        var game2 = new GorodaGame();

        var city1 = game1.makeFirstTurn();
        var city2 = game2.makeTurn(city1);
        var city3 = game1.makeTurn(city2);
        var city4 = game2.makeTurn(city3);

        assertTrue(game1.isCorrectTurn(city4));
    }

    @Test
    void isCorrectTurnFalse() {
        var game1 = new GorodaGame();
        var game2 = new GorodaGame();

        var city1 = game1.makeFirstTurn();
        var city2 = game2.makeTurn(city1);
        game1.makeTurn(city2);

        assertFalse(game2.isCorrectTurn(city2));
    }

    @Test
    void addUsedCity() {
        var game1 = new GorodaGame();
        var game2 = new GorodaGame();
        var city = game1.makeFirstTurn();
        game2.makeTurn(city);

        assertTrue(game2.isCityUsed(city));
    }
}