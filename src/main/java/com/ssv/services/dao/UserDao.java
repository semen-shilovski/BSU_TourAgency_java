package com.ssv.services.dao;


import com.ssv.models.auth.User;
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


public class UserDao implements Dao<User> {
    private final Class<User> entityClass = User.class;

    @Override
    public Optional<User> getById(Integer id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(entityClass);
            Root<User> root = cq.from(entityClass);

            cq.select(root).where(cb.equal(root.get("id"), id));
            TypedQuery<User> query = em.createQuery(cq);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            logger.error(e);
            logException(e);
        } finally {
            releaseEntityManager(em);
        }
        return Optional.empty();
    }

    public Optional<User> getByUsername(String username) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(entityClass);
            Root<User> root = cq.from(entityClass);

            cq.select(root).where(cb.equal(root.get("username"), username));
            TypedQuery<User> query = em.createQuery(cq);
            System.out.println("DB : " + query.getSingleResult().toString());
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
    public List<User> getAll() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(entityClass);
            Root<User> root = cq.from(entityClass);
            cq.select(root);
            TypedQuery<User> query = em.createQuery(cq);
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
    public void save(User user) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();

            tx.begin();

            em.persist(user);

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
    public void update(User user) {
        //no realisation
    }

    @Override
    public void delete(User user) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();

            tx.begin();
            em.detach(user);

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
