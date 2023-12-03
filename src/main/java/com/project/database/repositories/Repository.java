package com.project.database.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class Repository<T> implements AutoCloseable {
    protected Session session;
    private static SessionFactory factory;

    protected Repository() {
    }

    static {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.out.println("Database configuration error");
        }
    }

    public abstract T get(UUID id);

    public abstract Stream<T> getAll();

    protected void executeInsideTransaction(Consumer<Session> action) {
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            action.accept(session);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
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


    public void openSession() {
        session = factory.openSession();
    }

    @Override
    public void close() {
        session.close();
    }
}
