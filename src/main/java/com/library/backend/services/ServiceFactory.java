package com.library.backend.services;

import com.library.backend.exception.NonExistentServiceException;
import com.library.database.repositories.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code ServiceFactory} class is responsible for creating instances of different services
 * based on the specified service class. It uses a factory method pattern to instantiate the
 * appropriate service implementation based on the provided service class. This class provides
 * centralized service creation for the application.
 * <p>
 * The supported service types are defined by the {@code ServiceType} enum, and each type is associated
 * with a specific service implementation class. The supported services include:
 * <ul>
 *     <li>{@code ADMIN_SERVICE}: AdminService</li>
 *     <li>{@code LOG_IN_SERVICE}: LogInService</li>
 *     <li>{@code OPERATOR_SERVICE}: OperatorService</li>
 * </ul>
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * Service adminService = ServiceFactory.getService(AdminService.class);
 * }
 * </pre>
 * In this example, the {@code AdminService} instance will be created and returned by the factory.
 * <p>
 * The {@code Service} interface serves as a common interface for all service implementations.
 * Implementing classes should provide specific business logic for their respective services.
 *
 * @see com.library.backend.services.Service
 * @see com.library.backend.services.AdminService
 * @see com.library.backend.services.LogInService
 * @see com.library.backend.services.OperatorService
 */
public class ServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

    /**
     * An enum representing supported service types. Each type is associated with a specific
     * service implementation class.
     */
    @Getter
    @RequiredArgsConstructor
    private enum ServiceType {
        ADMIN_SERVICE(AdminService.class),
        LOG_IN_SERVICE(LogInService.class),
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
     * @throws NonExistentServiceException If the provided service class is not supported by the factory.
     *                                     This exception is thrown when there is no matching enum value.
     */
    public static <T extends Service> T getService(Class<T> serviceClass) {
        T service = null;

        try {
            switch (getServiceType(serviceClass)) {
                case ADMIN_SERVICE ->
                        service = serviceClass.cast(new AdminService(
                                BookRepository.getInstance(),
                                UserRepository.getInstance(),
                                BookInventoryRepository.getInstance(),
                                BookFormRepository.getInstance()));
                case LOG_IN_SERVICE -> service = serviceClass.cast(new LogInService(UserRepository.getInstance()));
                case OPERATOR_SERVICE ->
                        service = serviceClass.cast(new OperatorService(
                                BookRepository.getInstance(),
                                ReaderRepository.getInstance(),
                                BookInventoryRepository.getInstance(),
                                BookFormRepository.getInstance()));
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
