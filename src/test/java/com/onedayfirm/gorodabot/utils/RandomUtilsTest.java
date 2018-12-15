package com.onedayfirm.gorodabot.utils;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomUtilsTest {

    @Test
    void randomResultIsInCollection() {
        var collection = Set.of(1, 2, 3);

        var result = RandomUtils.random(collection);

        assertTrue(collection.contains(result));
    }

    @Test
    void randomEmptyCollection() {
        assertThrows(IllegalArgumentException.class, () -> RandomUtils.random(Collections.emptyList()));
    }
}
