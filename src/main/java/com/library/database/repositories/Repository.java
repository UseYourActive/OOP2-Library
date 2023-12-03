package com.library.database.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class Repository<T> implements AutoCloseable {
    private static final SessionFactory factory;
    private static final ThreadLocal<Session> threadLocalSession = new ThreadLocal<>();
    protected Session session;

    protected Repository() {
        this.session = getThreadLocalSession();
    }

    static {
        try {
            Configuration configuration = new Configuration().configure();
            factory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Error initializing Hibernate: " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    private Session getThreadLocalSession() {
        Session currentSession = threadLocalSession.get();
        if (currentSession == null || !currentSession.isOpen()) {
            currentSession = factory.openSession();
            threadLocalSession.set(currentSession);
        }
        return currentSession;
    }

    protected void executeInsideTransaction(Consumer<Session> action) {
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            action.accept(session);
            tx.commit();
        } catch (org.hibernate.HibernateException exception) {
            tx.rollback();
            throw new RuntimeException("Error during Hibernate transaction", exception);
        }
    }

    @Override
    public void close() {
        if (session != null && session.isOpen()) {
            session.close();
            threadLocalSession.remove();
        }
    }

    public void delete(T t) {
        executeInsideTransaction(session -> session.remove(t));
    }

    public boolean save(T t) {
        executeInsideTransaction(session -> session.persist(t));
        return true;
    }

    public void update(T t) {
        executeInsideTransaction(session -> session.merge(t));
    }

    public abstract T get(Long id);

    public abstract Stream<T> getAll();
}