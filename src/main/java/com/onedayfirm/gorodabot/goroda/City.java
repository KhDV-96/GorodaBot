package com.onedayfirm.gorodabot.goroda;

import java.util.HashMap;

public class City {

    private String name;
    private String alternativeName;

    City(String name) {
        this.name = name.toLowerCase();
        var firstChar = this.name.charAt(0);
        HashMap<Character, Character> letters = new HashMap<>() {
            {
                put('и', 'й');
                put('й', 'и');
                put('е', 'ё');
                put('ё', 'е');
            }
        };
        if (letters.containsKey(firstChar)) {
            this.alternativeName = letters.get(firstChar) + this.name.substring(1);
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
