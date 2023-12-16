package com.library.database.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class Repository<T> implements AutoCloseable {
    private static final SessionFactory sessionFactory;
    private static final ThreadLocal<Session> threadLocalSession = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(Repository.class);
    protected Session session;

    protected Repository() {
        this.session = getThreadLocalSession();
    }

    static {
        try {
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
            logger.info("Hibernate initialized successfully");
        } catch (Throwable ex) {
            logger.error("Error initializing Hibernate: {}", ex.getMessage(), ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public abstract Optional<T> findById(Long id);

    public abstract List<T> findAll();

    public abstract T getById(Long id);

    private Session getThreadLocalSession() {
        Session currentSession = threadLocalSession.get();
        if (currentSession == null || !currentSession.isOpen()) {
            currentSession = sessionFactory.openSession();
            threadLocalSession.set(currentSession);
            logger.debug("Opened new Hibernate session");
        }
        return currentSession;
    }

    protected final void executeInsideTransaction(Consumer<Session> action) {
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            action.accept(session);
            transaction.commit();
            logger.debug("Transaction committed successfully");
        } catch (org.hibernate.HibernateException exception) {
            logger.error("Error during Hibernate transaction", exception);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error during Hibernate transaction", exception);
        } finally {
            close();
        }
    }

    @Override
    public final void close() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
                threadLocalSession.remove();
                logger.debug("Closed Hibernate session");
            }
        } catch (Exception e) {
            logger.error("Error closing Hibernate session", e);
            throw new RuntimeException("Error closing Hibernate session", e);
        }
    }

    public final void delete(T object) {
        executeInsideTransaction(session -> session.remove(object));
        logger.info("Entity deleted successfully");
    }

    public final boolean save(T object) {
        executeInsideTransaction(session -> session.persist(object));
        logger.info("Entity saved successfully");
        return true;
    }

    public final void update(T object) {
        executeInsideTransaction(session -> session.merge(object));
        logger.info("Entity updated successfully");
    }
}