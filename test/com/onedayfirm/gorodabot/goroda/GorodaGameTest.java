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
        //assertEquals((char)game.getLastCharOfCity(firstCity), secondCity.charAt(0));
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
/*
    @Test
    void getLastCharOfCityb() {
        var game = new GorodaGame();
        var city = "Сочь";
        var letter = 'ч';

        assertEquals(letter, (char)game.getLastCharOfCity(city));
    }

    @Test
    void getLastCharOfCitybl() {
        var game = new GorodaGame();
        var city = "сочы";
        var letter = 'ч';

        assertEquals(letter, (char)game.getLastCharOfCity(city));
    }

    @Test
    void getLastChatOfCity_b() {
        var game = new GorodaGame();
        var city = "сочъ";
        var letter = 'ч';

        assertEquals(letter, (char)game.getLastCharOfCity(city));
    }

    @Test
    void getLastCharOfCityCorrect() {
        var game = new GorodaGame();
        var city = "сочи";
        var letter = 'и';

        assertEquals(letter, (char)game.getLastCharOfCity(city));
    } */
}