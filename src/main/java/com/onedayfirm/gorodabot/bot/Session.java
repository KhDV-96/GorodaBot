package com.onedayfirm.gorodabot.bot;

import com.onedayfirm.gorodabot.goroda.GorodaGame;

public class Session {

    private int id;
    private GorodaGame gorodaGame;

    public Session(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public GorodaGame getGorodaGame() {
        return gorodaGame;
    }

    public void setGorodaGame(GorodaGame gorodaGame) {
        this.gorodaGame = gorodaGame;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        return id == ((Session) other).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
