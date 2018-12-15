package com.onedayfirm.gorodabot.utils;

import java.util.Collection;
import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();

    public static <T> T random(Collection<? extends T> collection) {
        if (collection.size() > 0) {
            var index = RANDOM.nextInt(collection.size());
            for (var item : collection) {
                if (--index < 0)
                    return item;
            }
        }
        throw new IllegalArgumentException(collection + " is empty");
    }
}
