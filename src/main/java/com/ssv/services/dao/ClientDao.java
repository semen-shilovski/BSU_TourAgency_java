package com.ssv.services.dao;

import com.ssv.models.Client;
import com.ssv.models.Client_;
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
import static com.ssv.services.utils.Logger.logger;
import static com.ssv.services.utils.LoggerManager.logException;

public class ClientDao implements Dao<Client> {
    private final Class<Client> entityClass = Client.class;


    @Override
    public Optional<Client> getById(Integer id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Client> cq = cb.createQuery(entityClass);
            Root<Client> root = cq.from(entityClass);

            cq.select(root).where(cb.equal(root.get(Client_.id), id));
            TypedQuery<Client> query = em.createQuery(cq);
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
    public List<Client> getAll() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Client> cq = cb.createQuery(entityClass);
            Root<Client> root = cq.from(entityClass);
            cq.select(root);
            TypedQuery<Client> query = em.createQuery(cq);
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
    public void save(Client client) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();

            tx.begin();

            em.persist(client);

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
    public void update(Client client) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();

            tx.begin();
            Client client1 = em.find(Client.class, client.getId());
            client1.setAddress(client1.getAddress());
            client1.setName(client1.getName());
            client1.setAddress(client1.getAddress());
            em.merge(client1);

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
    public void delete(Client client) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();

            tx.begin();
            em.detach(client);

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
