package com.ssv.services.dao;

import com.ssv.models.Client;
import com.ssv.models.interfaces.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssv.services.utils.ConnectionPool.getConnection;
import static com.ssv.services.utils.ConnectionPool.releaseConnection;
import static com.ssv.services.utils.LoggerManager.logException;

public class ClientDao implements Dao<Client> {
    private static final String SELECT_BY_ID = """
            SELECT *
              FROM client cl
            where cl.id = ?
             """;

    private static final String SELECT_ALL_CLIENTS_SQL = """
                SELECT * FROM client
            """;

    private static final String INSERT_CLIENT_SQL = "INSERT INTO client (name, phonenumber, address) VALUES (?, ?, ?)";

    private static final String DELETE_BY_NAME = "DELETE FROM client WHERE name = ?";

    private static final String UPDATE_CLIENT_BY_NAME = "UPDATE client SET name = ?, phonenumber = ?, address = ? WHERE name = ?";


    @Override
    public Optional<Client> getById(Integer id) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapClientFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
        return Optional.empty();
    }

    private Client mapClientFromResultSet(ResultSet resultSet) throws SQLException {
        return Client.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .address(resultSet.getString("address"))
                .phoneNumber(resultSet.getString("phonenumber"))
                .build();
    }

    @Override
    public List<Client> getAll() {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        List<Client> clients = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_CLIENTS_SQL)) {
            while (resultSet.next()) clients.add(mapClientFromResultSet(resultSet));
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
        return clients;
    }

    @Override
    public void save(Client client) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENT_SQL)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getPhoneNumber());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(Client client) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_BY_NAME)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getPhoneNumber());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setString(4, client.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void delete(Client client) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_NAME)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}