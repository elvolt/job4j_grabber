package ru.job4j.quartz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionCreator {
    private ConnectionCreator() {
    }

    public static Connection create(Properties properties) {
        Connection result = null;
        try {
            Class.forName(properties.getProperty("driver-class-name"));
            result = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password")
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
