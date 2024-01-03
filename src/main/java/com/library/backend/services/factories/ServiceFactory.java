package com.library.backend.services.factories;

import com.library.backend.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

    public static Service getService(AbstractServiceFactory factory) {
        Service service = factory.createService();
        logger.info("Service {} created successfully", service.getClass().getSimpleName());
        return service;
    }
}


// nachini za rabota:
//    AbstractServiceFactory operatorServiceFactory = new OperatorServiceFactory();
//    Service operatorService = ServiceFactory.getService(operatorServiceFactory);
//
//    AbstractServiceFactory logInServiceFactory = new LogInServiceFactory();
//    Service logInService = ServiceFactory.getService(logInServiceFactory);
//
//    AbstractServiceFactory bookInventoryServiceFactory = new BookInventoryServiceFactory();
//    Service bookInventoryService = ServiceFactory.getService(bookInventoryServiceFactory);
