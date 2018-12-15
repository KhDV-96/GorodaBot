package com.onedayfirm.gorodabot.goroda;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KladrCityStorageTests {

    @Test
    void availableLetters() {
        var letters = KladrCitiesStorage.getInstance().getAvailableLetters();

        assertNotNull(letters);
        letters.forEach(letter -> assertTrue(Character.isLetter(letter)));
    }
}
