package services;

import models.Client;
import models.Tour;
import models.TourAgent;
import models.interfaces.Dao;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ClientDao implements Dao<Client> {
    private static final String SELECT_BY_ID = """
            SELECT *
              FROM client cl
            where cl.id = ?
             """;

    @Override
    public Optional<Client> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> getById(Integer id) {
        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapClientFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return null;
    }

    @Override
    public void save(Client client) {

    }

    @Override
    public void update(Client client) {

    }

    @Override
    public void delete(Client client) {

    }
}
