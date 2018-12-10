package com.onedayfirm.gorodabot.utils;

import java.util.Collection;
import java.util.Random;

public class RandomUtils {

    private static Random random = new Random();

    public static <T> T random(Collection<? extends T> collection) {
        var index = random.nextInt(collection.size());
        for (var item : collection) {
            if (--index < 0)
                return item;
        }
        throw new AssertionError();
    }
}
