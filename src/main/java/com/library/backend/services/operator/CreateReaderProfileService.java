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

public class CreateReaderProfileService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(CreateReaderProfileService.class);
    private final ReaderRepository readerRepository;

    public CreateReaderProfileService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public void createReader(String firstName, String middleName, String lastName, String phoneNumber, String email) throws IncorrectInputException {
        try {
            checkInput(firstName, middleName, lastName, phoneNumber);

            ReaderRating readerRating = ReaderRating.builder()
                    .rating(Ratings.NONE)
                    .currentValue(-1)
                    .coefficient(0)
                    //.readers(new ArrayList<>())
                    .build();

            Reader reader = Reader.builder()
                    .firstName(firstName)
                    .middleName(middleName)
                    .lastName(lastName)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .bookForms(Lists.newArrayList())
                    .readerRating(readerRating)
                    .build();

            // readerRating.getReaders().add(reader);

            //operatorService.saveRating(readerRating);
            readerRepository.save(reader);
            //operatorService.createReader(reader);

            logger.info("Created a new reader profile: {}", reader.getFullName());

        } catch (IncorrectInputException e) {
            logger.error("Failed to create reader profile due to incorrect input.", e);
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating a reader profile.", e);
            throw new RuntimeException("Failed to create reader profile.", e);
        }
    }

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
