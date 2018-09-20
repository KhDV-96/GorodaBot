package com.onedayfirm.gorodabot.goroda;

import java.util.HashSet;

public class GorodaGame {

    private HashSet<String> usedCities;

    GorodaGame() {
        usedCities = new HashSet<>();
    }

    public String makeTurn(String city) {
        return null;
    }

    void addUsedCity(String city) {
        usedCities.add(city);
    }

    boolean checkUsedCity(String city) {
        return usedCities.contains(city);
    }
}
