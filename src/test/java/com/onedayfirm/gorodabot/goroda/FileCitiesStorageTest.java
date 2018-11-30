package com.onedayfirm.gorodabot.goroda;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileCitiesStorageTest {

    @Test
    void getInstance() {
        var storage1 = FileCitiesStorage.getInstance();
        var storage2 = FileCitiesStorage.getInstance();

        assertNotNull(storage1);
        assertNotNull(storage2);
        assertEquals(storage1, storage2);
    }

    @Test
    void getCitiesByLetterValid() {
        var storage = FileCitiesStorage.getInstance();
        var cities = storage.getCitiesByLetter('ю');

        assertNotNull(cities);
        assertNotEquals(0, cities.size());
    }

    @Test
    void getCitiesByLetterInvalid() {
        var storage = FileCitiesStorage.getInstance();
        var cities = storage.getCitiesByLetter('\'');

        assertNull(cities);
    }

    @Test
    void getAvailableLetters() {
        var storage = FileCitiesStorage.getInstance();
        var letters = storage.getAvailableLetters();

        for (var letter : letters)
            assertNotNull(storage.getCitiesByLetter(letter));
    }
}