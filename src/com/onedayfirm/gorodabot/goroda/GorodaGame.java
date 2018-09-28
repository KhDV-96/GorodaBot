package com.onedayfirm.gorodabot.goroda;

import java.util.HashSet;
import java.util.Random;

public class GorodaGame {

    private HashSet<String> usedCities;
    private FileCitiesStorage storage;
    private String previousCity;

    GorodaGame() {
        usedCities = new HashSet<>();
        storage = FileCitiesStorage.getInstance();
    }

    public String makeTurn(String city) {
        var lastLetter = getLastCharOfCity(city);
        var citiesOnLastLetter = storage.getCitiesByLetter(lastLetter);
        if (citiesOnLastLetter == null) {
            return null;
        }
        for (var cityName : citiesOnLastLetter) {
            if (!isCityUsed(cityName)) {
                previousCity = city;
                addUsedCity(cityName);
                return cityName;
            }
        }
        return null;
    }

    public boolean isValidCity(String city) {
        var firstChar = city.charAt(0);
        var citiesList = storage.getCitiesByLetter(firstChar);
        if (citiesList == null) {
            return false;
        }
        if (previousCity == null) {
            return storage.getCitiesByLetter(firstChar).contains(city);
        }
        if (getLastCharOfCity(previousCity) == firstChar) {
            return storage.getCitiesByLetter(firstChar).contains(city);
        }
        return false;
    }

    public String makeFirstTurn() {
        var cities = storage.getCitiesByLetter('а');
        if (cities == null) {
            return null;
        }
        var random = new Random();
        var cityCount = random.nextInt(cities.toArray().length);
        previousCity = cities.get(cityCount);
        addUsedCity(previousCity);
        return previousCity;
    }

    public boolean isCityUsed(String city) {
        return usedCities.contains(city);
    }

    private void addUsedCity(String city) {
        usedCities.add(city);
    }

    private Character getLastCharOfCity(String city) {
        if ((city.endsWith("ь")) || (city.endsWith("ъ")) || (city.endsWith("ы"))) {
            return city.charAt(city.length() - 2);
        }
        return city.charAt(city.length() - 1);
    }
}