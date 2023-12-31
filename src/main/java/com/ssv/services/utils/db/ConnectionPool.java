package com.ssv.services.utils.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.ssv.services.utils.log.Logger.logger;
import static com.ssv.services.utils.log.LoggerManager.logException;


public class ConnectionPool {
    private static BlockingQueue<EntityManager> entityManagerPool;
    private static final int INITIAL_POOL_SIZE = 20;
    private static EntityManagerFactory entityManagerFactory;

    static {
        entityManagerPool = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);
        try {
            initializePool();
        } catch (Exception e) {
            logger.error(e);
            logException(e);
            throw new RuntimeException("Ошибка при инициализации пула EntityManager.", e);
        }
    }

    private static void initializePool() {
        entityManagerFactory = Persistence.createEntityManagerFactory("TourUnit");
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                entityManagerPool.add(entityManager);
            } catch (Exception e) {
                logger.error(e);
                logException(e);
                throw new RuntimeException("Ошибка при создании экземпляров EntityManager.", e);
            }
        }
    }

    public static EntityManager getEntityManager() throws InterruptedException {
        return entityManagerPool.take();
    }

    public static boolean releaseEntityManager(EntityManager entityManager) {
        if (entityManager != null) {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
            entityManager = entityManagerFactory.createEntityManager();
            return entityManagerPool.offer(entityManager);
        }
        return false;
    }

    public static void closeFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}

