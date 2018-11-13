package com.onedayfirm.gorodabot.goroda;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GorodaGameTest {

    private CitiesStorage storage = FileCitiesStorage.getInstance();

    @Test
    void makeTurnValid() {
        var game = new GorodaGame(storage);
        var firstCity = new City(game.makeFirstTurn());
        var secondCity = new City(game.makeTurn(firstCity));

        assertNotNull(firstCity);
        assertTrue(game.isCityUsed(secondCity));
    }

    @Test
    void makeTurnInvalid() {
        var game = new GorodaGame(storage);
        var city = game.makeTurn(new City("foobar"));

        assertNull(city);
    }

    /*@Test
    void getLastCharOfCity() {
        var game = new GorodaGame();
        var storage = FileCitiesStorage.getInstance();
        var availableLetters = storage.getAvailableLetters();
        var exceptionCity = new City("");
        loop:
        for (var letter : availableLetters) {
            for (var city : storage.getCitiesByLetter(letter)) {
                if (!availableLetters.contains(city.charAt(city.length() - 1))) {
                    exceptionCity = new City(city);
                    break loop;
                }
            }
        }
        var city = game.makeTurn(exceptionCity).toLowerCase();

        assertEquals(exceptionCity.getName().charAt(exceptionCity.getName().length() - 1), city.charAt(0));
    }*/

    @Test
    void isValidCityFalse() {
        var game = new GorodaGame(storage);
        var city = new City(game.makeFirstTurn());

        assertTrue(game.isValidCity(city));
        assertTrue(game.isCityUsed(city));
    }

    @Test
    void isValidCityFalse2() {
        var game = new GorodaGame(storage);

        assertFalse(game.isValidCity(new City("foobar")));
    }

    @Test
    void makeFirstTurn() {
        var game = new GorodaGame(storage);
        var city = new City(game.makeFirstTurn());

        assertNotNull(city);
        assertTrue(game.isCityUsed(city));
    }

    @Test
    void isCityUsedTrue() {
        var game = new GorodaGame(storage);
        var message = new City(game.makeFirstTurn());

        assertTrue(game.isCityUsed(message));
    }

    @Test
    void isCityUsedFalse() {
        var game = new GorodaGame(storage);

        assertFalse(game.isCityUsed(new City("foobar")));
    }

    @Test
    void isValidCityUpperCase() {
        var game = new GorodaGame(storage);
        var city = new City(game.makeFirstTurn());

        assertTrue(game.isValidCity(city));
    }

    @Test
    void isValidCityLowerCase() {
        var game = new GorodaGame(storage);
        var city = new StringBuilder(game.makeFirstTurn());
        city.setCharAt(0, Character.toLowerCase(city.charAt(0)));

        assertTrue(game.isValidCity(new City(city.toString())));
    }

    @Test
    void isValidCityWrongCityENG() {
        var game = new GorodaGame(storage);
        var city = new City("foobar");

        assertFalse(game.isValidCity(city));
    }

    @Test
    void isValidCityWrongCityRUS() {
        var game = new GorodaGame(storage);
        var city = new City("Фуубар");

        assertFalse(game.isValidCity(city));
    }

    @Test
    void isCorrectTurnTrue() {
        var game1 = new GorodaGame(storage);
        var game2 = new GorodaGame(storage);

        var city1 = new City(game1.makeFirstTurn());
        var city2 = new City(game2.makeTurn(city1));
        var city3 = new City(game1.makeTurn(city2));
        var city4 = new City(game2.makeTurn(city3));

        assertTrue(game1.isCorrectTurn(city4));
    }

    @Test
    void isCorrectTurnFalse() {
        var game1 = new GorodaGame(storage);
        game1.makeFirstTurn();

        assertFalse(game1.isCorrectTurn(new City("city")));
    }

    @Test
    void addUsedCity() {
        var game1 = new GorodaGame(storage);
        var game2 = new GorodaGame(storage);
        var city = new City(game1.makeFirstTurn());
        game2.makeTurn(city);

        assertTrue(game2.isCityUsed(city));
    }
}