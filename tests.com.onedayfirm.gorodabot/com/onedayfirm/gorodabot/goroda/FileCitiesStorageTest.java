package com.onedayfirm.gorodabot.goroda;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileCitiesStorageTest {

    @Test
    void getInstance() {
        var storage = FileCitiesStorage.getInstance();
        assertNotNull(storage);
    }

    @Test
    void getCitiesByLetter() {
        var storage = FileCitiesStorage.getInstance();
        assertEquals(storage.getCitiesByLetter('ю').toArray().length, 1);
    }

    @Test
    void getCitiesByLetterNotNull() {
        var storage = FileCitiesStorage.getInstance();
        assertNotNull(storage.getCitiesByLetter('а'));
    }
}