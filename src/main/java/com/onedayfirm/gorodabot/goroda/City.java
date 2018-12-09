package com.onedayfirm.gorodabot.goroda;

import com.onedayfirm.gorodabot.utils.Configurations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class City {

    private static final Map<Character, Character> SPECIAL_EQUALITIES = new HashMap<>();

    static {
        var letters = Arrays.stream(Configurations
                .getProperty("city.specialEqualities")
                .split(","))
                .map(s -> s.charAt(0))
                .toArray(Character[]::new);
        for (var i = 0; i < letters.length - 1; i += 2) {
            SPECIAL_EQUALITIES.put(letters[i], letters[i + 1]);
            SPECIAL_EQUALITIES.put(letters[i + 1], letters[i]);
        }
    }

    private String name;
    private String alternativeName;

    City(String name) {
        this.name = name.toLowerCase();
        var firstChar = this.name.charAt(0);
        if (SPECIAL_EQUALITIES.containsKey(firstChar)) {
            this.alternativeName = SPECIAL_EQUALITIES.get(firstChar) + this.name.substring(1);
        }
    }

    public String getName() {
        return name;
    }

    String getAlternativeName() {
        return alternativeName;
    }

    @Override
    public int hashCode() {
        if (alternativeName == null) {
            return name.hashCode();
        }
        return name.hashCode() + alternativeName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().isInstance(this)) {
            var city = (City) obj;
            if (this.alternativeName == null) {
                return city.name.equals(this.name);
            }
            return (city.name.equals(this.name) || this.alternativeName.equals(city.name));
        }
        return false;
    }
}
