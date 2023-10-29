package services.dao;

import models.TourAgent;
import models.interfaces.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static services.utils.ConnectionPool.getConnection;
import static services.utils.ConnectionPool.releaseConnection;
import static services.utils.LoggerManager.logException;

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
                    return Optional.of(mapTourAgentFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
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
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        List<TourAgent> tourAgents = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_TOUR_AGENT_SQL)) {
            while (resultSet.next()) tourAgents.add(mapTourAgentFromResultSet(resultSet));
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
        return tourAgents;
    }

    @Override
    public void save(TourAgent tourAgent) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TOUR_AGENT_SQL)) {
            preparedStatement.setString(1, tourAgent.getName());
            preparedStatement.setString(2, tourAgent.getPhoneNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(TourAgent tourAgent) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TOUR_AGENT_BY_NAME)) {
            preparedStatement.setString(1, tourAgent.getName());
            preparedStatement.setString(2, tourAgent.getPhoneNumber());
            preparedStatement.setString(3, tourAgent.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void delete(TourAgent tourAgent) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_NAME)) {
            preparedStatement.setString(1, tourAgent.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
