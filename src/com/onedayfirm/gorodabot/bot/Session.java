package com.onedayfirm.gorodabot.bot;

import com.onedayfirm.gorodabot.goroda.GorodaGame;

class Session {

    private GorodaGame gorodaGame;

    GorodaGame getGorodaGame() {
        return gorodaGame;
    }

    void setGorodaGame(GorodaGame gorodaGame) {
        this.gorodaGame = gorodaGame;
    }
}
