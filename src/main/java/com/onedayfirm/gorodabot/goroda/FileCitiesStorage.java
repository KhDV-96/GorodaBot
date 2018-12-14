package com.onedayfirm.gorodabot.goroda;

import com.onedayfirm.gorodabot.io.FileReader;
import com.onedayfirm.gorodabot.utils.Configurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FileCitiesStorage implements CitiesStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCitiesStorage.class);
    private static final String PATH = Configurations.getProperty("fileCitiesStorage.file");

    private static FileCitiesStorage instance = new FileCitiesStorage();

    private HashMap<Character, List<City>> cities;

    private FileCitiesStorage() {
        cities = new HashMap<>();
        FileReader.readLines(PATH).forEach(this::addCity);
        LOGGER.info("Cities loaded from the file {}", PATH);
    }

    public static FileCitiesStorage getInstance() {
        return instance;
    }

    @Override
    public List<City> getCitiesByLetter(char letter) {
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
        cities.get(symbol).add(new City(city));
    }
}
