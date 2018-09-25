package com.onedayfirm.gorodabot.goroda;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface CitiesStorage {

    List<String> getCitiesByLetter(char letter);

    Set<Character> getAvaivableFirstCharacters();
}
