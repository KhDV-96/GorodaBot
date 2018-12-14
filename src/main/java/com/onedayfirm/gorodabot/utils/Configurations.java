package com.onedayfirm.gorodabot.utils;

import com.onedayfirm.gorodabot.ExitException;
import com.onedayfirm.gorodabot.io.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Configurations {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configurations.class);
    private static final String PATH = "bot.properties";

    private static Configurations instance = new Configurations();

    private Properties properties;

    private Configurations() {
        properties = new Properties();
        load();
    }

    public static String getProperty(String key) {
        return instance.properties.getProperty(key);
    }

    private void load() {
        try (var in = FileReader.openForReading(PATH)) {
            properties.load(in);
        } catch (IOException exception) {
            LOGGER.error("Configurations weren't loaded", exception);
            throw new ExitException(exception, 1);
        }
        LOGGER.info("Configurations loaded");
    }
}
