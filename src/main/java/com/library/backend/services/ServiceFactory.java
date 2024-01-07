package com.library.backend.services;

import com.library.backend.exception.NonExistentServiceException;
import com.library.backend.services.admin.*;
import com.library.backend.services.operator.*;
import com.library.database.repositories.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

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

        BOOK_FORM_SHOW_CONTROLLER_SERVICE(BookFormShowControllerService.class),
        CREATE_BOOK_FORM_CONTROLLER_SERVICE(CreateBookFormControllerService.class),
        CREATE_READER_PROFILE_CONTROLLER_SERVICE(CreateReaderProfileControllerService.class),
        INBOX_CONTROLLER_SERVICE(InboxControllerService.class),
        OPERATOR_BOOKS_CONTROLLER_SERVICE(OperatorBooksControllerService.class),
        OPERATOR_READERS_CONTROLLER_SERVICE(OperatorReadersControllerService.class);

        private final Class<? extends Service> serviceClass;
    }

    public static <T extends Service> T getService(Class<T> serviceClass) {
        T service = null;
        try {
            switch (getServiceType(serviceClass)) {
                case LOG_IN_SERVICE -> service = serviceClass.cast(new LogInService(UserRepository.getInstance()));

                // Admin services
                case ADD_BOOK_QUANTITY_CONTROLLER_SERVICE -> service = serviceClass.cast(new AddBookQuantityControllerService(BookRepository.getInstance(), BookInventoryRepository.getInstance()));
                case ADMINISTRATOR_BOOKS_CONTROLLER_SERVICE -> service = serviceClass.cast(new AdministratorBooksControllerService(BookInventoryRepository.getInstance(), BookFormRepository.getInstance()));
                case ADMINISTRATOR_BOOKS_DIALOG_CONTROLLER_SERVICE -> service = serviceClass.cast(new AdministratorBooksDialogControllerService(BookInventoryRepository.getInstance(), BookFormRepository.getInstance()));
                case ADMINISTRATOR_OPERATORS_CONTROLLER_SERVICE -> service = serviceClass.cast(new AdministratorOperatorsControllerService(UserRepository.getInstance()));
                case BOOK_REGISTRATION_CONTROLLER_SERVICE -> service = serviceClass.cast(new BookRegistrationControllerService(BookInventoryRepository.getInstance(), BookRepository.getInstance()));
                case CREATE_OPERATOR_CONTROLLER_SERVICE -> service = serviceClass.cast(new CreateOperatorControllerService(UserRepository.getInstance()));

                // Operator services
                case BOOK_FORM_SHOW_CONTROLLER_SERVICE -> service = serviceClass.cast(new BookFormShowControllerService(BookRepository.getInstance(), ReaderRepository.getInstance(), EventNotificationRepository.getInstance()));
                case CREATE_BOOK_FORM_CONTROLLER_SERVICE -> service = serviceClass.cast(new CreateBookFormControllerService(ReaderRepository.getInstance(), BookFormRepository.getInstance(), BookRepository.getInstance()));
                case CREATE_READER_PROFILE_CONTROLLER_SERVICE -> service = serviceClass.cast(new CreateReaderProfileControllerService(ReaderRepository.getInstance()));
                case INBOX_CONTROLLER_SERVICE -> service = serviceClass.cast(new InboxControllerService(EventNotificationRepository.getInstance()));
                case OPERATOR_BOOKS_CONTROLLER_SERVICE -> service = serviceClass.cast(new OperatorBooksControllerService(BookFormRepository.getInstance(), BookInventoryRepository.getInstance(), EventNotificationRepository.getInstance(), BookRepository.getInstance()));
                case OPERATOR_READERS_CONTROLLER_SERVICE -> service = serviceClass.cast(new OperatorReadersControllerService(ReaderRepository.getInstance()));

                default -> throw new NonExistentServiceException("There is no such enum!");
            }
            logger.info("Service {} created successfully", serviceClass.getSimpleName());
        } catch (NonExistentServiceException e) {
            logger.error("Failed to create service {}: {}", serviceClass.getSimpleName(), e.getMessage());
        }

        return service;
    }

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
