package ru.sberstart.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.RunScript;
import ru.sberstart.exception.DaoException;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConfiguration {
    private static final String URL = "jdbc:h2:mem:bank";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final Object lock = new Object();
    private static DataSource DATA_SOURCE;

    public static Connection getConnection() {
        try {
            DATA_SOURCE = getDatasource();
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new DaoException("Internal service error!");
        }
    }

    private static DataSource getDatasource() {
        synchronized (lock) {
            if (DATA_SOURCE == null) {
                HikariConfig config = new HikariConfig();
                config.setUsername(USER);
                config.setPassword(PASSWORD);
                config.setJdbcUrl(URL);
                return new HikariDataSource(config);
            }
            return DATA_SOURCE;
        }
    }

    public void startupH2() {
        try {
            RunScript.execute(getConnection(),
                    new FileReader(getClass().getClassLoader()
                            .getResource("db/bank_ddl.sql").getFile()));
            RunScript.execute(getConnection(),
                    new FileReader(getClass().getClassLoader()
                            .getResource("db/bank_insert.sql").getFile()));
        } catch (SQLException | FileNotFoundException exception) {
            throw new DaoException("Data loading error");
        }

    }
}

