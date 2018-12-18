package com.onedayfirm.gorodabot.goroda;

import com.onedayfirm.gorodabot.utils.Configurations;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    void makeTurnNoCities() {
        var cities = new ArrayList<City>();
        cities.add(new City("city"));
        var storage = mock(CitiesStorage.class);
        when(storage.getAvailableLetters()).thenReturn(Set.of('c'));
        when(storage.getCitiesByLetter(anyChar()))
                .thenReturn(cities)
                .thenReturn(new ArrayList<>());
        var game = new GorodaGame(storage);
        game.makeFirstTurn();

        assertNull(game.makeTurn("city"));
    }

    @Test
    void makeTurnAllCitiesAreUsed() {
        var cities = new ArrayList<City>();
        cities.add(new City("city"));
        var storage = mock(CitiesStorage.class);
        when(storage.getAvailableLetters()).thenReturn(Set.of('c'));
        when(storage.getCitiesByLetter(anyChar())).thenReturn(cities);
        var game = new GorodaGame(storage);
        game.makeFirstTurn();

        assertNull(game.makeTurn("city"));
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
        var game = new GorodaGame(storage);
        game.makeFirstTurn();

        assertFalse(game.isCorrectTurn("city"));
    }

    @Test
    void isCorrectTurnFirstCity() {
        var game = new GorodaGame(storage);

        assertTrue(game.isCorrectTurn("city"));
    }

    @Test
    void isCorrectTurnAlternativeName() {
        var specialLetter = Configurations.getProperty("city.specialEqualities").charAt(0);
        var cities = new ArrayList<City>();
        cities.add(new City(Character.toString(specialLetter)));
        var storage = mock(CitiesStorage.class);
        when(storage.getAvailableLetters()).thenReturn(Set.of(specialLetter));
        when(storage.getCitiesByLetter(anyChar())).thenReturn(cities);
        var game = new GorodaGame(storage);

        game.makeFirstTurn();

        assertTrue(game.isCorrectTurn(storage.getCitiesByLetter(specialLetter).get(0).getAlternativeName()));
    }

    @Test
    void addUsedCity() {
        var game1 = new GorodaGame(storage);
        var game2 = new GorodaGame(storage);
        var city = game1.makeFirstTurn();
        game2.makeTurn(city);

        assertTrue(game2.isCityUsed(city));
    }

    @Test
    void getPreviousCityNull() {
        var game = new GorodaGame(storage);

        assertNull(game.getPreviousCity());
    }

    @Test
    void getPreviousCityNotNull() {
        var game = new GorodaGame(storage);
        var city = game.makeFirstTurn().toLowerCase();

        assertEquals(city, game.getPreviousCity());
    }
}