package com.onedayfirm.gorodabot.goroda;

import java.util.List;
import java.util.Set;

public interface CitiesStorage {

    List<City> getCitiesByLetter(char letter);

    Set<Character> getAvailableLetters();
}
