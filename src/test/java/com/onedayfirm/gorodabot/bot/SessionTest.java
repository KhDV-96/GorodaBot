package com.onedayfirm.gorodabot.bot;

import com.onedayfirm.gorodabot.goroda.GorodaGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SessionTest {

    @Test
    void initialization() {
        var id = 1;

        assertEquals(id, new Session(id).getId());
    }

    @Test
    void correctSavedGorodaGame() {
        var session = new Session(0);
        var game = new GorodaGame(null);

        session.setGorodaGame(game);

        assertEquals(game, session.getGorodaGame());
    }

    @Test
    void equalityByIdTrue() {
        assertEquals(new Session(0), new Session(0));
    }

    @Test
    void equalityByIdFalse() {
        assertNotEquals(new Session(0), new Session(1));
    }

    @Test
    void equalityByHashCodeTrue() {
        assertEquals(new Session(0).hashCode(), new Session(0).hashCode());
    }

    @Test
    void equalityByHashCodeFalse() {
        assertNotEquals(new Session(0).hashCode(), new Session(1).hashCode());
    }
}
