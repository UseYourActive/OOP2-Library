package com.library.backend.services;

import com.library.backend.exception.NonExistentServiceException;
import com.library.backend.services.admin.*;
import com.library.database.repositories.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

    /**
     * An enum representing supported service types. Each type is associated with a specific
     * service implementation class.
     */
    @Getter
    @RequiredArgsConstructor
    private enum ServiceType {
        LOG_IN_SERVICE(LogInService.class),
        ADD_BOOK_QUANTITY_CONTROLLER_SERVICE(AddBookQuantityControllerService.class),
        ADMINISTRATOR_BOOKS_CONTROLLER_SERVICE(AdministratorBooksControllerService.class),
        ADMINISTRATOR_BOOKS_DIALOG_CONTROLLER_SERVICE(AdministratorBooksDialogControllerService.class),
        ADMINISTRATOR_OPERATORS_CONTROLLER_SERVICE(AdministratorOperatorsControllerService.class),
        BOOK_REGISTRATION_CONTROLLER_SERVICE(BookRegistrationControllerService.class),
        CREATE_OPERATOR_CONTROLLER_SERVICE(CreateOperatorControllerService.class),

        OPERATOR_SERVICE(OperatorService.class);
        private final Class<? extends Service> serviceClass;
    }

    /**
     * Creates and returns an instance of the specified service class. This method is part of the
     * {@code ServiceFactory} and is responsible for instantiating services based on the provided
     * service class.
     * <p>
     * The method uses a switch statement to determine the type of service to create, relying on the
     * {@code ServiceType} enum associated with the provided service class. It then initializes the
     * appropriate service and logs the success or failure accordingly.
     *
     * @param serviceClass The class object representing the service to be instantiated.
     * @return An instance of the specified service class, or {@code null} if instantiation fails.
     */
    public static <T extends Service> T getService(Class<T> serviceClass) {
        T service = null;
        try {
            switch (getServiceType(serviceClass)) {
                case LOG_IN_SERVICE -> service = serviceClass.cast(new LogInService(UserRepository.getInstance()));

                case ADD_BOOK_QUANTITY_CONTROLLER_SERVICE -> service = serviceClass.cast(new AddBookQuantityControllerService(BookRepository.getInstance(), BookInventoryRepository.getInstance()));
                case ADMINISTRATOR_BOOKS_CONTROLLER_SERVICE -> service = serviceClass.cast(new AdministratorBooksControllerService(BookInventoryRepository.getInstance(), BookFormRepository.getInstance()));
                case ADMINISTRATOR_BOOKS_DIALOG_CONTROLLER_SERVICE -> service = serviceClass.cast(new AdministratorBooksDialogControllerService(BookInventoryRepository.getInstance(), BookFormRepository.getInstance()));
                case ADMINISTRATOR_OPERATORS_CONTROLLER_SERVICE -> service = serviceClass.cast(new AdministratorOperatorsControllerService(UserRepository.getInstance()));
                case BOOK_REGISTRATION_CONTROLLER_SERVICE -> service = serviceClass.cast(new BookRegistrationControllerService(BookInventoryRepository.getInstance(), BookRepository.getInstance()));
                case CREATE_OPERATOR_CONTROLLER_SERVICE -> service = serviceClass.cast(new CreateOperatorControllerService(UserRepository.getInstance()));
                case OPERATOR_SERVICE -> service = serviceClass.cast(new OperatorService(BookRepository.getInstance(), ReaderRepository.getInstance(), BookInventoryRepository.getInstance(), BookFormRepository.getInstance(), EventNotificationRepository.getInstance(), ReaderRatingRepository.getInstance()));

                default -> throw new NonExistentServiceException("There is no such enum!");
            }
            logger.info("Service {} created successfully", serviceClass.getSimpleName());
        } catch (NonExistentServiceException e) {
            logger.error("Failed to create service {}: {}", serviceClass.getSimpleName(), e.getMessage());
        }

        return service;
    }

    /**
     * Retrieves the {@code ServiceType} associated with the specified service class. This method is used
     * internally by the {@code ServiceFactory} to determine the type of service to create based on the
     * provided service class.
     * <p>
     * The method iterates over the supported {@code ServiceType} enum values and compares each type's
     * associated service class with the provided {@code serviceClass}. If a match is found, the
     * corresponding {@code ServiceType} is returned.
     *
     * @param serviceClass The class object representing the service for which to determine the type.
     * @return The {@code ServiceType} associated with the specified service class, or {@code null} if
     * the service class is not supported.
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
