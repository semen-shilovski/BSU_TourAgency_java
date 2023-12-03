package com.ssv.services.dao;


import com.ssv.models.Tour;
import com.ssv.models.Tour_;
import com.ssv.models.interfaces.Dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

import static com.ssv.services.utils.db.ConnectionPool.getEntityManager;
import static com.ssv.services.utils.db.ConnectionPool.releaseEntityManager;
import static com.ssv.services.utils.log.Logger.logger;
import static com.ssv.services.utils.log.LoggerManager.logException;

public class TourDao implements Dao<Tour> {
    private final Class<Tour> entityClass = Tour.class;


    public Optional<Tour> getByName(String name) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tour> cq = cb.createQuery(entityClass);
            Root<Tour> root = cq.from(entityClass);

            cq.select(root).where(cb.equal(root.get(Tour_.name), name));
            TypedQuery<Tour> query = em.createQuery(cq);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            logger.error(e);
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Tour> getById(Integer id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tour> cq = cb.createQuery(entityClass);
            Root<Tour> root = cq.from(entityClass);

            cq.select(root).where(cb.equal(root.get(Tour_.id), id));
            TypedQuery<Tour> query = em.createQuery(cq);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            logger.error(e);
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
        return Optional.empty();
    }

    @Override
    public List<Tour> getAll() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tour> cq = cb.createQuery(entityClass);
            Root<Tour> root = cq.from(entityClass);
            cq.select(root);
            TypedQuery<Tour> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            logger.error(e);
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
        return null;
    }

    @Override
    public void save(Tour tour) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();

            tx.begin();

            em.persist(tour);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error(e);
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
    }

    @Override
    public void update(Tour tour) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();

            tx.begin();
            Tour tour1 = em.find(Tour.class, tour.getName());
            tour1.setName(tour.getName());
            tour1.setType(tour.getType());
            tour1.setPrice(tour.getPrice());
            tour1.setLastMinute(tour.isLastMinute());
            tour1.setDiscountForRegularCustomers(tour.getDiscountForRegularCustomers());
            em.merge(tour1);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error(e);
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
    }

    @Override
    public void delete(Tour tour) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();

            tx.begin();
            em.detach(tour);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error(e);
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
    }
}
