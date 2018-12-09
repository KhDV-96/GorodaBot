package com.onedayfirm.gorodabot.goroda;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class KladrCityStorageTests {

    @Test
    void filterCitiesWithNoNumbers() {
        var cities = new ArrayList<City>();
        cities.add(new City("Краснодар"));
        cities.add(new City("Киров"));
        cities.add(new City("ваова"));
        var storage = new KladrCitiesStorage();
        var sorted = storage.filterCities(cities, 'к');
        assertEquals(2, sorted.size());
        assertTrue(sorted.contains(new City("Краснодар")));
        assertTrue(sorted.contains(new City("Киров")));
        assertFalse(sorted.contains(new City("ваова")));
    }

    @Test
    void filterCitiesWithNumbers() {
        var cities = new ArrayList<City>();
        cities.add(new City("Краснодар-19"));
        var storage = new KladrCitiesStorage();
        var sorted = storage.filterCities(cities, 'к');
        assertEquals(0, sorted.size());
    }

    @Test
    void filterMixedCities(){
        var cities = new ArrayList<City>();
        cities.add(new City("Краснодар-19"));
        cities.add(new City("Краснодар"));
        cities.add(new City("Киров"));
        cities.add(new City("ваова"));
        var storage = new KladrCitiesStorage();
        var sorted = storage.filterCities(cities, 'к');
        assertEquals(2, sorted.size());
        assertTrue(sorted.contains(new City("Краснодар")));
        assertTrue(sorted.contains(new City("Киров")));
        assertFalse(sorted.contains(new City("ваова")));
        assertFalse(sorted.contains(new City("Краснодар-19")));
    }

    @Test
    void availableLetters(){
        var storage = new KladrCitiesStorage();
        var letters = storage.getAvailableLetters();
        assertTrue(letters.contains('а'));
        assertTrue(letters.contains('я'));
        assertTrue(letters.contains('ё'));
        assertFalse(letters.contains('1'));
    }

    @Test
    void testGetCitiesByLetter(){
        var letter = 'й';
        var storage = new KladrCitiesStorage();
        var cities = storage.getCitiesByLetter(letter);
        assertTrue(cities.contains(new City("йошкар-ола")));
    }

}
