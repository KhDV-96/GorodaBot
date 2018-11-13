package com.onedayfirm.gorodabot.goroda;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KladrCitiesStorage implements CitiesStorage {

    private static final char[] AVAILABLE_LETTERS = "абвгдеёжзийклмнопрстуфхцчшщэюя".toCharArray();

    private static KladrCitiesStorage instance = new KladrCitiesStorage();

    private KladrRequester requester = new KladrRequester();

    public static KladrCitiesStorage getInstance() {
        return instance;
    }

    @Override
    public List<City> getCitiesByLetter(char letter) {
        try {
            var json = requester.makeGetRequest(letter);
            return requester.getNames(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
