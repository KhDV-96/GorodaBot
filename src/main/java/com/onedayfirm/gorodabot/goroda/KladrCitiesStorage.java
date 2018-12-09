package com.onedayfirm.gorodabot.goroda;

import com.onedayfirm.gorodabot.utils.Configurations;

import java.util.List;
import java.util.Set;
import java.util.*;
import java.util.stream.Collectors;

public class KladrCitiesStorage implements CitiesStorage {

    private static final Set<Character> AVAILABLE_LETTERS;

    private Map<Character, List<City>> cities = new WeakHashMap<>();

    private static KladrCitiesStorage instance = new KladrCitiesStorage();

    static {
        AVAILABLE_LETTERS = Configurations
                .getProperty("kladrCitiesStorage.letters")
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
    }

    private KladrRequester requester = new KladrRequester();

    public static KladrCitiesStorage getInstance() {
        return instance;
    }

    @Override
    public List<City> getCitiesByLetter(char letter) {
        if (cities.containsKey(letter)){
            return cities.get(letter);
        }
        try {
            var json = requester.makeGetRequest(letter);
            var names = filterCities(requester.getNames(json), letter);
            cities.put(letter, names);
            return names;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    List<City> filterCities(List<City> names, char letter) {
        return names
                .stream()
                .filter(name -> name.getName().toLowerCase()
                    .startsWith(Character.toString(letter)))
                .filter(name -> Character.isLetter(name.getName()
                    .charAt(name.getName().length()-1)))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Character> getAvailableLetters() {
        return AVAILABLE_LETTERS;
    }
}