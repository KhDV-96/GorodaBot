package com.onedayfirm.gorodabot.goroda;

import java.util.HashSet;
import java.util.Random;

public class GorodaGame {

    private HashSet<String> usedCities;
    private CitiesStorage storage;
    private String previousCity;

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
                previousCity = cityName;
                addUsedCity(cityName);
                return changeFirstLetterToUppercase(cityName);
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

    private boolean hasCorrectLetters(String city) {
        var firstChar = Character.toLowerCase(city.charAt(0));
        if (previousCity == null) {
            return true;
        }
        return getLastCharOfCity(previousCity) == firstChar;
    }

    public boolean isCorrectTurn(String city) {
        return isValidCity(city) && hasCorrectLetters(city);
    }

    public String makeFirstTurn() {
        var firstCharacters = storage.getAvailableLetters().toArray();
        var random = new Random();
        var characterNumber = random.nextInt(firstCharacters.length);
        var cities = storage.getCitiesByLetter((char) firstCharacters[characterNumber]);
        var cityNumber = random.nextInt(cities.size());
        previousCity = cities.get(cityNumber);
        addUsedCity(previousCity);
        return changeFirstLetterToUppercase(previousCity);
    }

    public boolean isCityUsed(String city) {
        return usedCities.contains(city.toLowerCase());
    }

    private void addUsedCity(String city) {
        usedCities.add(city);
    }

    private char getLastCharOfCity(String city) {
        var lastLetterIndex = city.length() - 1;
        if (!storage.getAvailableLetters().contains(city.charAt(lastLetterIndex))) {
            return city.charAt(lastLetterIndex - 1);
        }
        return city.charAt(lastLetterIndex);
    }

    private String changeFirstLetterToUppercase(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}
