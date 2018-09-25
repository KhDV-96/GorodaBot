package com.onedayfirm.gorodabot.goroda;

import java.util.HashSet;
import java.util.Random;

public class GorodaGame {

    private HashSet<String> usedCities;
    private FileCitiesStorage storage;
    private String previousCity = "";

    public GorodaGame() {
        usedCities = new HashSet<>();
        storage = FileCitiesStorage.getInstance();
    }

    public String makeTurn(String city) {
        city = city.toLowerCase();
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
        city = city.toLowerCase();
        var firstChar = city.charAt(0);
        var citiesList = storage.getCitiesByLetter(firstChar);
        if (citiesList == null) {
            return false;
        }
        return storage.getCitiesByLetter(firstChar).contains(city);
    }

    public boolean hasCorrectLetters(String city){
        var firstChar = city.charAt(0);
        if (previousCity == null) {
            return true;
        }
        return getLastCharOfCity(previousCity) == firstChar;
    }

    public String makeFirstTurn() {
        var firstCharacters = storage.getAvaivableFirstCharacters().toArray();
        var random = new Random();
        if (firstCharacters == null){
            return null;
        }
        var characterNumber = random.nextInt(firstCharacters.length);
        var cities = storage.getCitiesByLetter((char)firstCharacters[characterNumber]);
        if (cities == null) {
            return null;
        }
        var cityNumber = random.nextInt(cities.toArray().length);
        previousCity = cities.get(cityNumber);
        addUsedCity(previousCity);
        return previousCity;
    }

    public boolean isCityUsed(String city) {
        return usedCities.contains(city);
    }

    private void addUsedCity(String city) {
        usedCities.add(city);
    }

    public Character getLastCharOfCity(String city) {
        if ((city.endsWith("ь")) || (city.endsWith("ъ")) || (city.endsWith("ы"))) {
            return city.charAt(city.length() - 2);
        }
        return city.charAt(city.length() - 1);
    }
}
