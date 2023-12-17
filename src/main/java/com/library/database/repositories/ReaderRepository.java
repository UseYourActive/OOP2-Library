package com.library.database.repositories;

import com.library.database.entities.Reader;
import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ReaderRepository extends Repository<Reader> {
    private static final Logger logger = LoggerFactory.getLogger(ReaderRepository.class);
    private static volatile ReaderRepository instance;

    private ReaderRepository() {
    }

    public static ReaderRepository getInstance() {
        if (instance == null) {
            synchronized (ReaderRepository.class) {
                if (instance == null) {
                    instance = new ReaderRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Optional<Reader> findById(Long id) throws HibernateException {
        try {
            Reader reader = session.get(Reader.class, id);
            if (reader != null) {
                logger.info("Successfully found reader with id: {}", id);
            } else {
                logger.info("No reader found with id: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(reader);
        } catch (HibernateException e) {
            logger.error("Error finding reader by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<Reader> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT r FROM Reader r", Reader.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all readers", e);
            throw e;
        }
    }

    @Override
    public Reader getById(Long id) throws HibernateException {
        Reader reader = session.get(Reader.class, id);
        if (reader != null) {
            logger.info("Successfully found reader with id: {}", id);
        }
        return reader;
    }

    private <T> Optional<T> executeQuery(String query, String paramName, String paramValue, Class<T> resultType) throws HibernateException {
        try {
            return Optional.ofNullable(
                    session.createQuery(query, resultType)
                            .setParameter(paramName, paramValue)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            logger.info("No result found with {} : {}", paramName, paramValue);
            return Optional.empty();
        } catch (HibernateException e) {
            logger.error("Error executing query: {}", query, e);
            throw e;
        }
    }

    public Optional<Reader> findByEmail(String email) throws HibernateException {
        String query = "SELECT r FROM Reader r WHERE r.email = :email";
        return executeQuery(query, "email", email, Reader.class);
    }

    public Optional<Reader> findByPhoneNumber(String phoneNumber) throws HibernateException {
        String query = "SELECT r FROM Reader r WHERE r.phone_number = :phone_number";
        return executeQuery(query, "phone_number", phoneNumber, Reader.class);
    }
}
