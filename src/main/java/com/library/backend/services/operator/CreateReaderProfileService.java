package com.library.backend.services.operator;

import com.google.common.collect.Lists;
import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.Service;
import com.library.database.entities.Reader;
import com.library.database.entities.ReaderRating;
import com.library.database.enums.Ratings;
import com.library.database.repositories.ReaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code CreateReaderProfileService} class provides services for creating new reader profiles.
 * It includes functionality for validating input and creating a reader profile with an associated reader rating.
 *
 * @see Service
 */
public class CreateReaderProfileService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(CreateReaderProfileService.class);
    private final ReaderRepository readerRepository;

    /**
     * Constructs a {@code CreateReaderProfileService} instance with the specified reader repository.
     *
     * @param readerRepository The repository for managing reader data.
     */
    public CreateReaderProfileService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    /**
     * Creates a new reader profile with the provided information.
     *
     * @param firstName   The first name of the reader.
     * @param middleName  The middle name of the reader.
     * @param lastName    The last name of the reader.
     * @param phoneNumber The phone number of the reader.
     * @param email       The email address of the reader.
     * @throws IncorrectInputException If the input validation fails.
     */
    public void createReader(String firstName, String middleName, String lastName, String phoneNumber, String email) throws IncorrectInputException {
        try {
            checkInput(firstName, middleName, lastName, phoneNumber);

            // Create a new reader rating with default values
            ReaderRating readerRating = ReaderRating.builder()
                    .rating(Ratings.NONE)
                    .currentValue(-1)
                    .coefficient(0)
                    .build();

            // Create a new reader with the provided information
            Reader reader = Reader.builder()
                    .firstName(firstName)
                    .middleName(middleName)
                    .lastName(lastName)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .bookForms(Lists.newArrayList())
                    .readerRating(readerRating)
                    .build();

            // Save the new reader profile to the repository
            readerRepository.save(reader);

            logger.info("Created a new reader profile: {}", reader.getFullName());

        } catch (IncorrectInputException e) {
            logger.error("Failed to create reader profile due to incorrect input.", e);
            throw e;
        }
    }

    /**
     * Validates the input parameters for creating a new reader profile.
     *
     * @param firstName   The first name of the reader.
     * @param middleName  The middle name of the reader.
     * @param lastName    The last name of the reader.
     * @param phoneNumber The phone number of the reader.
     * @throws IncorrectInputException If any of the input parameters are empty.
     */
    private void checkInput(String firstName, String middleName, String lastName, String phoneNumber) throws IncorrectInputException {
        if (firstName.isEmpty())
            throw new IncorrectInputException("Please enter first name.");

        if (middleName.isEmpty())
            throw new IncorrectInputException("Please enter middle name.");

        if (lastName.isEmpty())
            throw new IncorrectInputException("Please enter last name.");

        if (phoneNumber.isEmpty())
            throw new IncorrectInputException("Please enter phone number.");

        logger.info("Input validation passed for creating a new reader profile.");
    }
}
