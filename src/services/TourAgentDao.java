package services;

import models.TourAgent;
import models.interfaces.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourAgentDao implements Dao<TourAgent> {

    private static final String SELECT_BY_ID = """
            SELECT *
              FROM tour_agent ta
            where ta.id = ?
             """;

    private static final String SELECT_ALL_TOUR_AGENT_SQL = """
                SELECT * FROM tour_agent
            """;

    private static final String INSERT_TOUR_AGENT_SQL = "INSERT INTO tour_agent (name, phonenumber) VALUES (?, ?)";

    private static final String DELETE_BY_NAME = "DELETE FROM tour_agent WHERE name = ?";

    private static final String UPDATE_TOUR_AGENT_BY_NAME = "UPDATE tour_agent SET name = ?, phonenumber = ? WHERE name = ?";

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
        List<TourAgent> tourAgents = new ArrayList<>();
        try (Connection connection = JdbcConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_TOUR_AGENT_SQL)) {
            while (resultSet.next()) tourAgents.add(mapTourAgentFromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tourAgents;
    }

    @Override
    public void save(TourAgent tourAgent) {
        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TOUR_AGENT_SQL)) {
            preparedStatement.setString(1, tourAgent.getName());
            preparedStatement.setString(2, tourAgent.getPhoneNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(TourAgent tourAgent) {
        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TOUR_AGENT_BY_NAME)) {
            preparedStatement.setString(1, tourAgent.getName());
            preparedStatement.setString(2, tourAgent.getPhoneNumber());
            preparedStatement.setString(3, tourAgent.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(TourAgent tourAgent) {
        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_NAME)) {
            preparedStatement.setString(1, tourAgent.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
