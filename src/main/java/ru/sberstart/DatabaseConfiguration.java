package ru.sberstart;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConfiguration {
    private static final String URL = "jdbc:mysql://localhost:3306/bank";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    public DataSource getDatasource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setJdbcUrl(URL);
        return new HikariDataSource(config);
    }
}

