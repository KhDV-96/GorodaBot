package com.onedayfirm.gorodabot.utils;

import java.util.Random;

public class ArrayUtils {

    public static <T> void mix(T[] array) {
        var random = new Random();
        for (var i = 0; i < array.length; i++) {
            var j = random.nextInt(array.length);
            var tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }
}
