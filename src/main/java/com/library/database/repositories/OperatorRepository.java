package com.library.database.repositories;

import com.library.database.entities.Operator;
import com.library.database.entities.Reader;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
public class OperatorRepository extends Repository<Operator>{

    private static final Logger logger = LoggerFactory.getLogger(OperatorRepository.class);

    @Override
    public Optional<Operator> findById(Long id) {
        Operator operator = session.get(Operator.class, id);
        return Optional.ofNullable(operator);
    }

    @Override
    public List<Operator> findAll() {
        return session.createQuery("SELECT o FROM Operator o", Operator.class).getResultList();
    }

    public Operator findByUsername(String username) throws Exception {
        return session.createQuery("Select o from Operator o", Operator.class).stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new Exception("Operator not found"));
    }

}
