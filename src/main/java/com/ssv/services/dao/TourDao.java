package com.ssv.services.dao;


import com.ssv.models.Client;
import com.ssv.models.Tour;
import com.ssv.models.TourAgent;
import com.ssv.models.interfaces.Dao;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.ssv.services.utils.HibernateConnectionPool.getSession;
import static com.ssv.services.utils.HibernateConnectionPool.releaseSession;
import static com.ssv.services.utils.LoggerManager.logException;

public class TourDao implements Dao<Tour> {
    private static final String SELECT_ALL_TOURS_SQL = "Tour.selectAll";
    private static final String SELECT_BY_NAME = "Tour.selectByName";
    private static final String SELECT_BY_ID = "Tour.selectById";
    private static final String DELETE_BY_NAME = "Tour.deleteTourByName";
    private static final String UPDATE_TOUR_BY_NAME = "Tour.updateTourByName";


    public Optional<Tour> getByName(String name) {
        var session = getSession();
        try {
            Query query = session.getNamedQuery(SELECT_BY_NAME);
            query.setParameter("name", name);
            return Optional.of((Tour) query.getSingleResult());
        } catch (Exception e) {
            logException(e);
        } finally {
            releaseSession(session);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Tour> getById(Integer id) {
        var session = getSession();
        try {
            Query query = session.getNamedQuery(SELECT_BY_ID);
            query.setParameter("id", id);
            return Optional.of((Tour) query.getSingleResult());
        } catch (Exception e) {
            logException(e);
        } finally {
            releaseSession(session);
        }
        return Optional.empty();
    }

    @Override
    public List<Tour> getAll() {
        List<Tour> entries = new ArrayList<>();
        var session = getSession();
        try {
            Query query = session.getNamedQuery(SELECT_ALL_TOURS_SQL);
            return query.list();
        } catch (Exception e) {
            logException(e);
        } finally {
            releaseSession(session);
        }
        return entries;
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
        var session = getSession();
        var tx = session.beginTransaction();
        try {
            session.persist(tour);
            tx.commit();
        } catch (Exception e) {
            if (Objects.nonNull(tx)) tx.rollback();
            logException(e);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public void update(Tour tour) {
        var session = getSession();
        var tx = session.beginTransaction();
        try {
            Query query = session.getNamedQuery(UPDATE_TOUR_BY_NAME);
            query.setParameter("name", tour.getName());
            query.setParameter("type", tour.getType());
            query.setParameter("price", tour.getPrice());
            query.setParameter("isLastMinute", tour.isLastMinute());
            query.setParameter("discount", tour.getDiscountForRegularCustomers());
            query.setParameter("clientId", tour.getClient().getId());
            query.setParameter("tourAgentId", tour.getTourAgent().getId());
            query.setParameter("nameEq", tour.getName());
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (Objects.nonNull(tx)) tx.rollback();
            logException(e);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public void delete(Tour tour) {
        var session = getSession();
        var tx = session.beginTransaction();
        try {
            Query query = session.getNamedQuery(DELETE_BY_NAME);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (Objects.nonNull(tx)) tx.rollback();
            logException(e);
        } finally {
            releaseSession(session);
        }
    }
}
