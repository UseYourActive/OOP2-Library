package com.library.backend.operations;

import com.library.backend.exception.AdminNotFoundException;
import com.library.backend.exception.OperatorNotFoundException;
import com.library.backend.exception.ReaderNotFoundException;
import com.library.database.entities.Admin;
import com.library.database.entities.Operator;
import com.library.database.entities.Reader;
import com.library.database.entities.base.User;
import com.library.database.repositories.AdminRepository;
import com.library.database.repositories.OperatorRepository;
import com.library.database.repositories.ReaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;

public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private AdminRepository adminRepository;
    private OperatorRepository operatorRepository;
    private ReaderRepository readerRepository;

    public AuthenticationService(
            AdminRepository adminRepository,
            OperatorRepository operatorRepository,
            ReaderRepository readerRepository
    ) {
        this.adminRepository = adminRepository;
        this.operatorRepository = operatorRepository;
        this.readerRepository = readerRepository;
    }

    public User authenticate(String username, String password, User role) throws AuthenticationException {
        try {
            User authenticatedUser;
            if (role instanceof Admin) {
                authenticatedUser = authenticateAdmin(username, password);
            } else if (role instanceof Operator) {
                authenticatedUser = authenticateOperator(username, password);
            } else if (role instanceof Reader) {
                authenticatedUser = authenticateReader(username, password);
            } else {
                throw new AuthenticationException("Invalid user role");
            }

            logger.info("User authenticated successfully: {}", authenticatedUser.getUsername());
            return authenticatedUser;

        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", username, e);
            throw e;
        }
    }

    private User authenticateAdmin(String username, String password) throws AuthenticationException {
        try {
            Admin admin = adminRepository.findByUsername(username);
            if (admin != null && admin.getPassword().equals(password)) {
                return admin;
            } else {
                throw new AuthenticationException("Invalid credentials for Admin");
            }
        } catch (AdminNotFoundException e) {
            throw new AuthenticationException("Admin not found");
        }
    }

    private User authenticateOperator(String username, String password) throws AuthenticationException {
        try {
            Operator operator = operatorRepository.findByUsername(username);
            if (operator != null && operator.getPassword().equals(password)) {
                return operator;
            } else {
                throw new AuthenticationException("Invalid credentials for Operator");
            }
        } catch (OperatorNotFoundException e) {
            throw new AuthenticationException("Operator not found");
        }
    }

    private User authenticateReader(String username, String password) throws AuthenticationException {
        try {
            Reader reader = readerRepository.findByUsername(username);
            if (reader != null && reader.getPassword().equals(password)) {
                return reader;
            } else {
                throw new AuthenticationException("Invalid credentials for Reader");
            }
        } catch (ReaderNotFoundException e) {
            throw new AuthenticationException("Reader not found");
        }
    }
}
