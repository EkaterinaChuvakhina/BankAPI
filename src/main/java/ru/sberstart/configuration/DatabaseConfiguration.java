package ru.sberstart.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.RunScript;
import org.h2.tools.Server;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;

public class DatabaseConfiguration {
    private static final String URL = "jdbc:h2:mem:bank";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final Object lock = new Object();
    private static DataSource DATA_SOURCE;

    public static DataSource getDatasource() {
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

    public void startupH2() throws SQLException, FileNotFoundException {
        Server tcpServer = Server.createTcpServer(
                "-tcpPort", "9091", "-tcpAllowOthers");
        tcpServer.start();

        RunScript.execute(getDatasource().getConnection(),
                new FileReader(getClass().getClassLoader()
                        .getResource("db/bank_ddl.sql").getFile()));
    }
}

