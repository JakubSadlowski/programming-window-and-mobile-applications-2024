package org.js.programmingwindowapplications.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

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

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void testConnection() {
        try {
            getSessionFactory().openSession();
            System.out.println("Successfully connected to the database!");
        } catch (Exception e) {
            System.err.println("Failed to connect to the database!");
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
