package com.onedayfirm.gorodabot.goroda;


import java.util.List;
import java.util.Set;

public class APICitiesStorage implements CitiesStorage {

    private KladrRequester requester = new KladrRequester();

    private static APICitiesStorage instance = new APICitiesStorage();

    public static APICitiesStorage getInstance() {
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
        return FileCitiesStorage.getInstance().getAvailableLetters();
    }
}
