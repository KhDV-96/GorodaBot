package com.onedayfirm.gorodabot.goroda;

import java.util.HashSet;
import java.util.List;

import static com.onedayfirm.gorodabot.utils.RandomUtils.random;
import static java.util.Collections.shuffle;

public class GorodaGame {

    private HashSet<City> usedCities;
    private CitiesStorage storage;
    private City previousCity;

    public GorodaGame(CitiesStorage storage) {
        usedCities = new HashSet<>();
        this.storage = storage;
    }

    public String makeTurn(String message) {
        var city = new City(message);
        return makeTurn(city);
    }

    private String makeTurn(City city) {
        var lastLetter = getLastCharOfCity(city);
        var cities = storage.getCitiesByLetter(lastLetter);
        if (cities == null)
            return null;
        var nextCity = getRandomCity(cities);
        if (nextCity == null)
            return null;
        addUsedCity(city);
        addUsedCity(nextCity);
        previousCity = nextCity;
        return changeFirstLetterToUppercase(nextCity);
    }

    public boolean isValidCity(String message) {
        var city = new City(message);
        return isValidCity(city);
    }

    private boolean isValidCity(City city) {
        var firstChar = city.getName().charAt(0);
        var citiesList = storage.getCitiesByLetter(firstChar);
        if (citiesList == null) {
            return false;
        }
        return citiesList.contains(city);
    }

    private boolean hasCorrectLetters(City city) {
        if (previousCity == null) {
            return true;
        }
        var firstChar = Character.toLowerCase(city.getName().charAt(0));
        if (city.getAlternativeName() != null) {
            var alternativeFirstChar = Character.toLowerCase(city.getAlternativeName().charAt(0));
            return getLastCharOfCity(previousCity) == firstChar ||
                    getLastCharOfCity(previousCity) == alternativeFirstChar;
        }
        return getLastCharOfCity(previousCity) == firstChar;
    }

    public boolean isCorrectTurn(String message) {
        var city = new City(message);
        return isCorrectTurn(city);
    }

    private boolean isCorrectTurn(City city) {
        return hasCorrectLetters(city);
    }

    public String makeFirstTurn() {
        var letter = random(storage.getAvailableLetters());
        var cities = storage.getCitiesByLetter(letter);
        previousCity = getRandomCity(cities);
        addUsedCity(previousCity);
        return changeFirstLetterToUppercase(previousCity);
    }

    public boolean isCityUsed(String message) {
        var city = new City(message);
        return isCityUsed(city);
    }

    private boolean isCityUsed(City city) {
        return usedCities.contains(city);
    }

    private void addUsedCity(City city) {
        usedCities.add(city);
    }

    private char getLastCharOfCity(City city) {
        var cityName = city.getName();
        var lastLetterIndex = cityName.length() - 1;
        if (!storage.getAvailableLetters().contains(cityName.charAt(lastLetterIndex))) {
            return cityName.charAt(lastLetterIndex - 1);
        }
        return cityName.charAt(lastLetterIndex);
    }

    private String changeFirstLetterToUppercase(City word) {
        return Character.toUpperCase(word.getName().charAt(0)) + word.getName().substring(1);
    }

    private City getRandomCity(List<City> cities) {
        shuffle(cities);
        for (var city : cities) {
            if (!isCityUsed(city))
                return city;
        }
        return null;
    }

    public String getPreviousCity() {
        return previousCity == null ? null : previousCity.getName();
    }
}
