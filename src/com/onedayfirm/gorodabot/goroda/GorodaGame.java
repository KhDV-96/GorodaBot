package com.onedayfirm.gorodabot.goroda;

import java.util.HashSet;

public class GorodaGame {

    private HashSet<String> usedCities;
    private FileCitiesStorage storage;

    GorodaGame() {
        usedCities = new HashSet<>();
        storage = FileCitiesStorage.getInstance();
    }

    public String makeTurn(String city) {
        var lastLetter = getLastCharOfCity(city);
        var citiesOnLastLetter = storage.getCitiesByLetter(lastLetter);
        for (var cityName : citiesOnLastLetter) {
            if (!isCityUsed(cityName)) {
                addUsedCity(cityName);
                return cityName;
            }
        }
        return null;
    }

    public boolean isValidCity(String city) {
        var firstChar = city.charAt(0);
        return storage.getCitiesByLetter(firstChar).contains(city);
    }

    private void addUsedCity(String city) {
        usedCities.add(city);
    }

    public boolean isCityUsed(String city) {
        return usedCities.contains(city);
    }

    private Character getLastCharOfCity(String city) {
        if ((city.endsWith("ь")) || (city.endsWith("ъ")) || (city.endsWith("ы"))) {
            return city.charAt(city.length() - 2);
        }
        return city.charAt(city.length() - 1);
    }
}
