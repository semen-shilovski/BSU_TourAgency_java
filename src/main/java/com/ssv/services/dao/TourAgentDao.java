package com.ssv.services.dao;


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

public class TourAgentDao implements Dao<TourAgent> {

    private static final String SELECT_BY_ID = "TourAgent.selectById";
    private static final String SELECT_ALL_TOUR_AGENT_SQL = "TourAgent.selectAll";
    private static final String DELETE_BY_NAME = "TourAgent.deleteTourAgentByName";
    private static final String UPDATE_TOUR_AGENT_BY_NAME = "TourAgent.updateTourAgentByName";

    @Override
    public Optional<TourAgent> getById(Integer id) {
        var session = getSession();
        try {
            Query query = session.getNamedQuery(SELECT_BY_ID);
            query.setParameter("id", id);
            return Optional.of((TourAgent) query.getSingleResult());
        } catch (Exception e) {
            logException(e);
        } finally {
            releaseSession(session);
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
        List<TourAgent> entries = new ArrayList<>();
        var session = getSession();
        try {
            Query query = session.getNamedQuery(SELECT_ALL_TOUR_AGENT_SQL);
            return query.list();
        } catch (Exception e) {
            logException(e);
        } finally {
            releaseSession(session);
        }
        return entries;
    }

    @Override
    public void save(TourAgent tourAgent) {
        var session = getSession();
        var tx = session.beginTransaction();
        try {
            session.persist(tourAgent);
            tx.commit();
        } catch (Exception e) {
            if (Objects.nonNull(tx)) tx.rollback();
            logException(e);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public void update(TourAgent tourAgent) {
        var session = getSession();
        var tx = session.beginTransaction();
        try {
            Query query = session.getNamedQuery(UPDATE_TOUR_AGENT_BY_NAME);
            query.setParameter("name", tourAgent.getName());
            query.setParameter("phonenumber", tourAgent.getPhoneNumber());
            query.setParameter("nameEq", tourAgent.getName());
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
    public void delete(TourAgent tourAgent) {
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
