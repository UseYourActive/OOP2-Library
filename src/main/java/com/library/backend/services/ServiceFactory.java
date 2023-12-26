package com.library.backend.services;

import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.ReaderRepository;
import com.library.database.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

    @Getter
    @RequiredArgsConstructor
    private enum ServiceType {
        ADMIN_SERVICE(AdminService.class),
        LOG_IN_SERVICE(LogInService.class),
        OPERATOR_SERVICE(OperatorService.class);
        private final Class<? extends Service> serviceClass;
    }

    public static Service getService(Class<? extends Service> serviceClass) {
        Service service;

        try {
            switch (getServiceType(serviceClass)) {
                case ADMIN_SERVICE -> service = serviceClass.cast(new AdminService(BookRepository.getInstance(), UserRepository.getInstance(), BookInventoryRepository.getInstance()));
                case LOG_IN_SERVICE -> service = serviceClass.cast(new LogInService(UserRepository.getInstance()));
                case OPERATOR_SERVICE -> service = serviceClass.cast(new OperatorService(BookRepository.getInstance(), ReaderRepository.getInstance()));
                default -> throw new RuntimeException("There is no such enum");
            }
            logger.info("Service {} created successfully", serviceClass.getSimpleName());
        } catch (Exception e) {
            logger.error("Failed to create service {}: {}", serviceClass.getSimpleName(), e.getMessage());
            throw e;
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
