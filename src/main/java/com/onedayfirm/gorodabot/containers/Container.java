package com.onedayfirm.gorodabot.containers;

import com.onedayfirm.gorodabot.io.FileReader;
import com.onedayfirm.gorodabot.utils.Configurations;

import java.util.HashMap;
import java.util.Map;

public abstract class Container<T> {

    private static final String SEPARATOR = Configurations.getProperty("container.separator");

    private Map<String, T> content;

    Container(String path) {
        content = new HashMap<>();
        load(path);
    }

    public T get(String key) {
        return content.get(key);
    }

    abstract T parse(String value);

    private void load(String path) {
        FileReader.readLines(path).forEach(line -> {
            var block = line.split(SEPARATOR);
            content.put(block[0], parse(block[1]));
        });
    }
}
