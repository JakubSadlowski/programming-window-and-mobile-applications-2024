package org.js.programmingwindowapplications.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static EntityManagerFactory buildEntityManagerFactory() {
        return getSessionFactory().unwrap(EntityManagerFactory.class);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void testConnection() {
        try {
            getEntityManager().getTransaction().begin();
            System.out.println("Successfully connected to the database!");
        } catch (Exception e) {
            System.err.println("Failed to connect to the database!");
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
