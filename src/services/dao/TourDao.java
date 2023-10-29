package services.dao;

import models.Client;
import models.Tour;
import models.TourAgent;
import models.interfaces.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static services.utils.ConnectionPool.getConnection;
import static services.utils.ConnectionPool.releaseConnection;
import static services.utils.LoggerManager.logException;

public class TourDao implements Dao<Tour> {
    private static final String INSERT_TOUR_SQL = "INSERT INTO tours (name, type, price, is_last_minute, discount, client_id, tour_agent_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_TOURS_SQL = """
                SELECT * FROM tours tr
            """;
    private static final String SELECT_BY_NAME = """
            SELECT *
              FROM tours tr
            where tr.name = ?
             """;
    private static final String DELETE_BY_NAME = "DELETE FROM tours WHERE name = ?";
    private static final String UPDATE_TOUR_BY_NAME = "UPDATE tours SET name = ?, type = ?, price = ?, is_last_minute = ?, discount = ?, client_id = ?, tour_agent_id = ? WHERE name = ?";


    public Optional<Tour> getByName(String name) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapTourFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Tour> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Tour> getAll() {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        List<Tour> tours = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_TOURS_SQL)) {
            while (resultSet.next()) tours.add(mapTourFromResultSet(resultSet));
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
        return tours;
    }

    private Tour mapTourFromResultSet(ResultSet resultSet) throws SQLException {
        return new Tour.TourBuilder()
                .name(resultSet.getString("name"))
                .type(resultSet.getString("type"))
                .price(resultSet.getDouble("price"))
                .lastMinute(resultSet.getBoolean("is_last_minute"))
                .discountForRegularCustomers(resultSet.getDouble("discount"))
                .client(Client.builder()
                        .id(resultSet.getInt("client_id"))
                        .build())
                .tourAgent(TourAgent.builder()
                        .id(resultSet.getInt("tour_agent_id"))
                        .build())
                .build();
    }


    @Override
    public void save(Tour tour) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TOUR_SQL)) {
            preparedStatement.setString(1, tour.getName());
            preparedStatement.setString(2, tour.getType());
            preparedStatement.setDouble(3, tour.getPrice());
            preparedStatement.setBoolean(4, tour.isLastMinute());
            preparedStatement.setDouble(5, tour.getDiscountForRegularCustomers());
            preparedStatement.setInt(6, tour.getClient().getId());
            preparedStatement.setInt(7, tour.getTourAgent().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(Tour tour) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TOUR_BY_NAME)) {
            preparedStatement.setString(1, tour.getName());
            preparedStatement.setString(2, tour.getType());
            preparedStatement.setDouble(3, tour.getPrice());
            preparedStatement.setBoolean(4, tour.isLastMinute());
            preparedStatement.setDouble(5, tour.getDiscountForRegularCustomers());
            preparedStatement.setInt(6, tour.getClient().getId());
            preparedStatement.setInt(7, tour.getTourAgent().getId());
            preparedStatement.setString(8, tour.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void delete(Tour tour) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_NAME)) {
            preparedStatement.setString(1, tour.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
