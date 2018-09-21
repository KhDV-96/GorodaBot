package com.onedayfirm.gorodabot.goroda;

import java.util.List;

public interface CitiesStorage {

    List<String> getCitiesByLetter(char letter);
}
