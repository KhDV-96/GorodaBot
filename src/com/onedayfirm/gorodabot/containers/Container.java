package com.onedayfirm.gorodabot.containers;

import com.onedayfirm.gorodabot.io.FileReader;

import java.util.HashMap;
import java.util.Map;

public abstract class Container<T> {

    private static final String SEPARATOR = " ~?? ";

    private Map<String, T> content;

    Container(String path) {
        content = new HashMap<>();
        FileReader.readLines(path).forEach(line -> {
            var block = line.split(SEPARATOR);
            content.put(block[0], parse(block[1]));
        });
    }

    public T get(String key) {
        return content.get(key);
    }

    abstract T parse(String value);
}
