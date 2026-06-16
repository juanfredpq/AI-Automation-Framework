package com.ai.automation.framework.database;

import com.ai.automation.framework.config.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseManager {

    private static Connection connection;

    private DatabaseManager() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        ConfigManager.getInstance().getDatabaseUrl(),
                        ConfigManager.getInstance().getDatabaseUsername(),
                        ConfigManager.getInstance().getDatabasePassword());
            } catch (SQLException ex) {
                throw new RuntimeException("Unable to create database connection", ex);
            }
        }
        return connection;
    }

    public static ResultSet executeQuery(String sql) {
        try {
            Statement statement = getConnection().createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException ex) {
            throw new RuntimeException("Database query failed: " + sql, ex);
        }
    }

    public static int executeUpdate(String sql) {
        try {
            Statement statement = getConnection().createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException("Database update failed: " + sql, ex);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
            connection = null;
        }
    }
}
