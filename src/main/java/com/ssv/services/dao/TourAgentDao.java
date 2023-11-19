package com.ssv.services.dao;


import com.ssv.models.TourAgent;
import com.ssv.models.TourAgent_;
import com.ssv.models.interfaces.Dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

import static com.ssv.services.utils.ConnectionPool.getEntityManager;
import static com.ssv.services.utils.ConnectionPool.releaseEntityManager;
import static com.ssv.services.utils.LoggerManager.logException;

public class TourAgentDao implements Dao<TourAgent> {
    private final Class<TourAgent> entityClass = TourAgent.class;

    @Override
    public Optional<TourAgent> getById(Integer id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<TourAgent> cq = cb.createQuery(entityClass);
            Root<TourAgent> root = cq.from(entityClass);

            cq.select(root).where(cb.equal(root.get(TourAgent_.id), id));
            TypedQuery<TourAgent> query = em.createQuery(cq);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
        return Optional.empty();
    }

    @Override
    public List<TourAgent> getAll() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<TourAgent> cq = cb.createQuery(entityClass);
            Root<TourAgent> root = cq.from(entityClass);
            cq.select(root);
            TypedQuery<TourAgent> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
        return null;
    }

    @Override
    public void save(TourAgent tourAgent) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();

            tx.begin();

            em.persist(tourAgent);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
    }

    @Override
    public void update(TourAgent tourAgent) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();

            tx.begin();
            TourAgent tourAgent1 = em.find(TourAgent.class, tourAgent.getId());
            tourAgent1.setName(tourAgent.getName());
            tourAgent1.setPhoneNumber(tourAgent.getPhoneNumber());
            em.merge(tourAgent1);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
    }

    @Override
    public void delete(TourAgent tourAgent) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();

            tx.begin();
            em.detach(tourAgent);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
    }
}
