package com.library.database.repositories;

import org.hibernate.HibernateException;
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

    public abstract Optional<T> findById(Long id) throws HibernateException;

    public abstract List<T> findAll() throws HibernateException;

    public abstract T getById(Long id) throws HibernateException;

    private Session getThreadLocalSession() throws org.hibernate.HibernateException {
        Session currentSession = threadLocalSession.get();
        if (currentSession == null || !currentSession.isOpen()) {
            currentSession = sessionFactory.openSession();
            threadLocalSession.set(currentSession);
            logger.debug("Opened new Hibernate session");
        }
        return currentSession;
    }

    protected final void executeInsideTransaction(Consumer<Session> action) throws org.hibernate.HibernateException {
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
            throw new org.hibernate.HibernateException("Error during Hibernate transaction", exception);
        }
    }

    @Override
    public final void close() throws org.hibernate.HibernateException {
        try {
            if (session != null && session.isOpen()) {
                session.close();
                threadLocalSession.remove();
                logger.debug("Closed Hibernate session");
            }
        } catch (org.hibernate.HibernateException e) {
            logger.error("Error closing Hibernate session", e);
            throw new org.hibernate.HibernateException("Error closing Hibernate session", e);
        }
    }

    public final void delete(T object) throws org.hibernate.HibernateException {
        executeInsideTransaction(session -> session.remove(object));
        logger.info("Entity deleted successfully");
    }

    public final boolean save(T object) throws org.hibernate.HibernateException {
        executeInsideTransaction(session -> session.persist(object));
        logger.info("Entity saved successfully");
        return true;
    }

    public final void update(T object) throws org.hibernate.HibernateException {
        executeInsideTransaction(session -> session.merge(object));
        logger.info("Entity updated successfully");
    }
}