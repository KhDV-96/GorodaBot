package com.onedayfirm.gorodabot.goroda;

import java.util.*;
import java.util.stream.Collectors;

public class KladrCitiesStorage implements CitiesStorage {

    private static final char[] AVAILABLE_LETTERS = "абвгдеёжзийклмнопрстуфхцчшщэюя".toCharArray();

    private Map<Character, List<City>> cities = new WeakHashMap<>();

    private static KladrCitiesStorage instance = new KladrCitiesStorage();

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

    private List<City> filterCities(List<City> names, char letter) {
        return names
                .stream()
                .filter(name -> name.getName().toLowerCase()
                .startsWith(Character.toString(letter)))
                .collect(Collectors.toList());
    }


    @Override
    public Set<Character> getAvailableLetters() {
        var set = new HashSet<Character>();
        for (var letter : AVAILABLE_LETTERS) {
            set.add(letter);
        }
        return set;
    }
}