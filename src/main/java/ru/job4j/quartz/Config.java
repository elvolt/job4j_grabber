package ru.job4j.quartz;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private Config() {
    }

    public static Properties load(String resourcesFilename) {
        Properties config = new Properties();
        try (InputStream io = AlertRabbit.class.getClassLoader().getResourceAsStream(resourcesFilename)) {
            config.load(io);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }
}
