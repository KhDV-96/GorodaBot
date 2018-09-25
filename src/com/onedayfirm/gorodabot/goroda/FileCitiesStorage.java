package com.onedayfirm.gorodabot.goroda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FileCitiesStorage implements CitiesStorage {

    private static final String PATH = "resources/parsed_cities.txt";

    private static FileCitiesStorage instance = new FileCitiesStorage();

    private HashMap<Character, List<String>> cities;

    private FileCitiesStorage() {
        cities = new HashMap<>();
        loadCities().forEach(this::addCityToStorage);
    }

    public static FileCitiesStorage getInstance() {
        return instance;
    }

    @Override
    public List<String> getCitiesByLetter(char letter) {
        if (cities.containsKey(letter)) {
            return cities.get(letter);
        }
        return null;
    }

    @Override
    public Set<Character> getAvaivableFirstCharacters() {
        if (cities == null){
            return null;
        }
        return cities.keySet();
    }


    private void addCityToStorage(String city) {
        city = city.toLowerCase();
        var symbol = city.charAt(0);
        if (!cities.containsKey(symbol)) {
            cities.put(symbol, new ArrayList<>());
        }
        cities.get(symbol).add(city);
    }

    private List<String> loadCities() {
        try {
            return Files.readAllLines(Paths.get(PATH));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
