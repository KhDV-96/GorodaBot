package com.onedayfirm.gorodabot.goroda;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityTest {

    @Test
    void check_Equals(){
        var city = new City("абазай");
        var city2 = new City("абазай");

        assertEquals(city, city2);
    }

    @Test
    void check_Equals_alternativeName(){
        var city = new City("Йошкар");
        var city2 = new City("Иошкар");

        assertEquals(city, city2);
    }

    @Test
    void check_Equal_cities_differentCases(){
        var city = new City("Йошкар");
        var city2 = new City("иошкар");
        assertEquals(city, city2);
        assertEquals(city.hashCode(), city2.hashCode());
    }



}
