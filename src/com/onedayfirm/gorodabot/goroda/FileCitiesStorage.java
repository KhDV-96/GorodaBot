package com.onedayfirm.gorodabot.goroda;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileCitiesStorage implements CitiesStorage {

    private static FileCitiesStorage instance;
    private HashMap<Character, List<String>> cities;

    private FileCitiesStorage() {
        cities = new HashMap<>();
        setCitiesFromFile("resources/parsed_cities.txt");
    }

    public static FileCitiesStorage getInstance() {
        if (instance == null) {
            instance = new FileCitiesStorage();
        }
        return instance;
    }

    @Override
    public List<String> getCitiesByLetter(char letter) {
        if (cities.containsKey(letter)) {
            return cities.get(letter);
        }
        return null;
    }

    private void setCitiesFromFile(String path) {
        var citiesList = loadCities(path);
        for (var city : citiesList) {
            addCityToStorage(city);
        }
    }

    private void addCityToStorage(String city) {
        city = city.toLowerCase();
        var symbol = city.charAt(0);
        if (!cities.containsKey(symbol)) {
            cities.put(symbol, new ArrayList<>());
        }
        cities.get(symbol).add(city);
    }

    private List<String> loadCities(String path) {
        try {
            return Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
