package com.onedayfirm.gorodabot.goroda;

import com.onedayfirm.gorodabot.utils.Configurations;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class KladrCitiesStorage implements CitiesStorage {

    private static final Set<Character> AVAILABLE_LETTERS;

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
        var json = requester.makeGetRequest(letter);
        return json == null ? null : requester.getNames(json);
    }

    @Override
    public Set<Character> getAvailableLetters() {
        return AVAILABLE_LETTERS;
    }
}
