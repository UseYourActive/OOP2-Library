package com.library.backend.services;

import com.library.backend.exception.NonExistentServiceException;
import com.library.backend.services.admin.*;
import com.library.backend.services.operator.*;
import com.library.database.repositories.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code ServiceFactory} class is responsible for creating instances of various service classes
 * based on the provided service type. It encapsulates the logic of instantiating services with
 * their required dependencies and provides a centralized point for obtaining service instances.
 */
public class ServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

    /**
     * Enumeration of supported service types along with their corresponding implementation classes.
     */
    @Getter
    @RequiredArgsConstructor
    private enum ServiceType {
        LOG_IN_SERVICE(LogInService.class),

        ADD_BOOK_QUANTITY_SERVICE(AddBookQuantityService.class),
        ADMINISTRATOR_BOOKS_SERVICE(AdministratorBooksService.class),
        ADMINISTRATOR_BOOKS_DIALOG_SERVICE(AdministratorBooksDialogService.class),
        ADMINISTRATOR_OPERATORS_SERVICE(AdministratorOperatorsService.class),
        BOOK_REGISTRATION_SERVICE(BookRegistrationService.class),
        CREATE_OPERATOR_SERVICE(CreateOperatorService.class),

        BOOK_FORM_SHOW_SERVICE(BookFormShowService.class),
        CREATE_BOOK_FORM_SERVICE(CreateBookFormService.class),
        CREATE_READER_PROFILE_SERVICE(CreateReaderProfileService.class),
        INBOX_SERVICE(InboxService.class),
        OPERATOR_BOOKS_SERVICE(OperatorBooksService.class),
        OPERATOR_READERS_SERVICE(OperatorReadersService.class);

        private final Class<? extends Service> serviceClass;
    }

    /**
     * Retrieves an instance of the specified service class.
     *
     * @param <T>           The type of service.
     * @param serviceClass  The class of the service to be instantiated.
     * @return An instance of the specified service class.
     * @throws NonExistentServiceException If the provided service class is not found in the enumeration.
     */
    public static <T extends Service> T getService(Class<T> serviceClass) {
        T service = null;
        try {
            switch (getServiceType(serviceClass)) {
                case LOG_IN_SERVICE -> service = serviceClass.cast(new LogInService(UserRepository.getInstance()));

                // Admin services
                case ADD_BOOK_QUANTITY_SERVICE -> service = serviceClass.cast(new AddBookQuantityService(BookRepository.getInstance(), BookInventoryRepository.getInstance()));
                case ADMINISTRATOR_BOOKS_SERVICE -> service = serviceClass.cast(new AdministratorBooksService(BookInventoryRepository.getInstance(), BookFormRepository.getInstance()));
                case ADMINISTRATOR_BOOKS_DIALOG_SERVICE -> service = serviceClass.cast(new AdministratorBooksDialogService(BookInventoryRepository.getInstance(), BookFormRepository.getInstance()));
                case ADMINISTRATOR_OPERATORS_SERVICE -> service = serviceClass.cast(new AdministratorOperatorsService(UserRepository.getInstance()));
                case BOOK_REGISTRATION_SERVICE -> service = serviceClass.cast(new BookRegistrationService(BookInventoryRepository.getInstance(), BookRepository.getInstance()));
                case CREATE_OPERATOR_SERVICE -> service = serviceClass.cast(new CreateOperatorService(UserRepository.getInstance()));

                // Operator services
                case BOOK_FORM_SHOW_SERVICE -> service = serviceClass.cast(new BookFormShowService(BookRepository.getInstance(), ReaderRepository.getInstance(), EventNotificationRepository.getInstance()));
                case CREATE_BOOK_FORM_SERVICE -> service = serviceClass.cast(new CreateBookFormService(ReaderRepository.getInstance(), BookFormRepository.getInstance(), BookRepository.getInstance()));
                case CREATE_READER_PROFILE_SERVICE -> service = serviceClass.cast(new CreateReaderProfileService(ReaderRepository.getInstance()));
                case INBOX_SERVICE -> service = serviceClass.cast(new InboxService(EventNotificationRepository.getInstance()));
                case OPERATOR_BOOKS_SERVICE -> service = serviceClass.cast(new OperatorBooksService(BookFormRepository.getInstance(), BookInventoryRepository.getInstance(), EventNotificationRepository.getInstance(), BookRepository.getInstance()));
                case OPERATOR_READERS_SERVICE -> service = serviceClass.cast(new OperatorReadersService(ReaderRepository.getInstance()));

                default -> throw new NonExistentServiceException("There is no such enum!");
            }
            logger.info("Service {} created successfully", serviceClass.getSimpleName());
        } catch (NonExistentServiceException e) {
            logger.error("Failed to create service {}: {}", serviceClass.getSimpleName(), e.getMessage());
        }

        return service;
    }

    /**
     * Determines the service type associated with the provided service class.
     *
     * @param serviceClass The class of the service.
     * @return The corresponding service type.
     */
    private static ServiceType getServiceType(Class<? extends Service> serviceClass) {
        ServiceType processorType = null;

        for (ServiceType type : ServiceType.values()) {
            if (type.serviceClass == serviceClass) {
                processorType = type;
                break;
            }
        }

        return processorType;
    }
}
