package com.library.database.repositories;

import com.library.database.entities.Operator;
import com.library.database.entities.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class OperatorRepository extends Repository<Operator>{

    private static final Logger logger = LoggerFactory.getLogger(OperatorRepository.class);

    public OperatorRepository() {
        super();
    }

    @Override
    public Operator findById(Long id) {
        return session.find(Operator.class, id);
    }

    @Override
    public Stream<Operator> findAll() {
        return session.createQuery("SELECT o FROM Operator o", Operator.class).getResultStream();
    }

    public Operator findByUsername(String username) throws Exception {
        return session.createQuery("Select o from Operator o", Operator.class).stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new Exception("Operator not found"));
    }

}
