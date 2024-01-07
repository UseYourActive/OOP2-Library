package com.library.database.repositories;

import com.library.database.entities.ReaderRating;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ReaderRatingRepository extends Repository<ReaderRating>{
    @Override
    public Optional<ReaderRating> findById(Long id) throws HibernateException {
        try {
            ReaderRating readerRating = session.get(ReaderRating.class, id);
            if (readerRating != null) {
                logger.info("Successfully found author with id: {}", id);
            } else {
                logger.info("No author found with id: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(readerRating);
        } catch (HibernateException e) {
            logger.error("Error finding author by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<ReaderRating> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT a FROM ReaderRating a", ReaderRating.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all authors", e);
            throw e;
        }
    }

    @Override
    public ReaderRating getById(Long id) throws HibernateException {
        ReaderRating readerRating = session.get(ReaderRating.class, id);
        if (readerRating != null) {
            logger.info("Successfully found author with id: {}", id);
        }
        return readerRating;
    }

    private static final Logger logger = LoggerFactory.getLogger(ReaderRatingRepository.class);
    private static volatile ReaderRatingRepository instance;

    private ReaderRatingRepository() {
    }

    /**
     * Gets the singleton instance of the {@code AuthorRepository}.
     *
     * @return The singleton instance of the {@code AuthorRepository}.
     */
    public static ReaderRatingRepository getInstance() {
        if (instance == null) {
            synchronized (ReaderRatingRepository.class) {
                if (instance == null) {
                    instance = new ReaderRatingRepository();
                }
            }
        }
        return instance;
    }

}
