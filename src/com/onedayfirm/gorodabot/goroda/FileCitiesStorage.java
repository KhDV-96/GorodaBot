package com.onedayfirm.gorodabot.goroda;

import com.onedayfirm.gorodabot.io.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FileCitiesStorage implements CitiesStorage {

    private static final String PATH = "resources/cities.txt";

    private static FileCitiesStorage instance = new FileCitiesStorage();

    private HashMap<Character, List<String>> cities;

    private FileCitiesStorage() {
        cities = new HashMap<>();
        FileReader.readLines(PATH).forEach(this::addCity);
    }

    static FileCitiesStorage getInstance() {
        return instance;
    }

    @Override
    public List<String> getCitiesByLetter(char letter) {
        return cities.getOrDefault(letter, null);
    }

    @Override
    public Set<Character> getAvailableLetters() {
        return cities.keySet();
    }


    private void addCity(String city) {
        city = city.toLowerCase();
        var symbol = city.charAt(0);
        if (!cities.containsKey(symbol)) {
            cities.put(symbol, new ArrayList<>());
        }
        cities.get(symbol).add(city);
    }
}
