package com.onedayfirm.gorodabot.goroda;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import static com.onedayfirm.gorodabot.utils.ArrayUtils.mix;

public class GorodaGame {

    private HashSet<City> usedCities;
    private CitiesStorage storage;
    private City previousCity;

    public GorodaGame() {
        usedCities = new HashSet<>();
        storage = FileCitiesStorage.getInstance();
    }

    public String makeTurn(String message){
        var city = new City(message);
        return makeTurn(city);
    }

    public String makeTurn(City city) {
        var lastLetter = getLastCharOfCity(city);
        var cities = storage.getCitiesByLetter(lastLetter);
        if (cities == null)
            return null;
        var nextCity = getRandomCityByLetter(cities);
        if (nextCity == null)
            return null;
        addUsedCity(city);
        addUsedCity(nextCity);
        previousCity = nextCity;
        return changeFirstLetterToUppercase(nextCity);
    }

    public boolean isValidCity(String message){
        var city = new City(message);
        return isValidCity(city);
    }

    public boolean isValidCity(City city) {
        var firstChar = city.getName().charAt(0);
        var citiesList = storage.getCitiesByLetter(firstChar);
        if (citiesList == null){
            return false;
        }
        return citiesList.contains(city);
    }

    public boolean hasCorrectLetters(City city) {
        if (previousCity == null) {
            return true;
        }
        var firstChar = Character.toLowerCase(city.getName().charAt(0));
        if (city.getAlternativeName() != null){
            var alternativeFirstChar = Character.toLowerCase(city.getAlternativeName().charAt(0));
            return getLastCharOfCity(previousCity) == firstChar ||
                    getLastCharOfCity(previousCity) == alternativeFirstChar;
        }
        return getLastCharOfCity(previousCity) == firstChar;
    }

    public boolean isCorrectTurn(String message){
        var city = new City(message);
        return isCorrectTurn(city);
    }
    public boolean isCorrectTurn(City city) {
        return isValidCity(city) && hasCorrectLetters(city);
    }

    public String makeFirstTurn() {
        var firstCharacters = storage.getAvailableLetters().toArray();
        var random = new Random();
        var characterNumber = random.nextInt(firstCharacters.length);
        var cities = storage.getCitiesByLetter((char) firstCharacters[characterNumber]);
        previousCity = getRandomCityByLetter(cities);
        addUsedCity(previousCity);
        return changeFirstLetterToUppercase(previousCity);
    }

    public boolean isCityUsed(String message){
        var city = new City(message);
        return isCityUsed(city);
    }

    public boolean isCityUsed(City city) {
        return usedCities.contains(city);
    }

    public void addUsedCity(City city) {
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
    private City getRandomCityByLetter(Collection<City> cities) {
        var citiesArray = cities.toArray(new City[0]);
        mix(citiesArray);
        for (var city : citiesArray) {
            if (!isCityUsed(city))
                return city;
        }
        return null;
    }
}
