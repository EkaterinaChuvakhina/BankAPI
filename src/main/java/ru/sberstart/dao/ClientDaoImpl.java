package ru.sberstart.dao;

import ru.sberstart.entity.Card;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ClientDaoImpl implements ClientDao {
    private final DataSource dataSource;

    public ClientDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    private Connection getConnection() {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
