package services;

import models.TourAgent;
import models.interfaces.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TourAgentDao implements Dao<TourAgent> {

    private static final String SELECT_BY_ID = """
            SELECT *
              FROM tour_agent ta
            where ta.id = ?
             """;

    @Override
    public Optional<TourAgent> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<TourAgent> getById(Integer id) {
        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapTourAgentFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private TourAgent mapTourAgentFromResultSet(ResultSet resultSet) throws SQLException {
        return TourAgent.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .phoneNumber(resultSet.getString("phonenumber"))
                .build();
    }

    @Override
    public List<TourAgent> getAll() {
        return null;
    }

    @Override
    public void save(TourAgent tourAgent) {

    }

    @Override
    public void update(TourAgent tourAgent) {

    }

    @Override
    public void delete(TourAgent tourAgent) {

    }
}
