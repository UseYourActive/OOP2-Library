package com.library.libraryproject.backend.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class DAO<T> implements AutoCloseable {
    private SessionFactory sessionFactory;
    private Session session;

    protected abstract Optional<T> get(UUID id);
    protected abstract List<T> findAll();

//    static {
//        try {
//            sessionFactory = new Configuration().configure().buildSessionFactory();
//        }catch (Throwable ex){
//            System.out.println("error");
//        }
//    }
//
//    public static Session openSession() {
//        return sessionFactory.openSession();
//    }
//
//    public static void closeSession() {
//        sessionFactory.close();
//    }

    protected DAO() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    protected void executeInsideTransaction(Consumer<Session> action) {
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            action.accept(session);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        }
    }

    public void openSession() {
        this.session = sessionFactory.openSession();
    }

    public void close() {
        this.session.close();
    }

    public boolean save(T t) {
        executeInsideTransaction(session -> session.persist(t));
        return true;
    }

    public void update(T t) {
        executeInsideTransaction(session -> session.merge(t));
    }

    public void delete(T t) {
        executeInsideTransaction(session -> session.remove(t));
    }
}
