package com.library.backend.services;

import com.library.database.repositories.BookRepository;
import com.library.database.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ServiceFactory {
        @Getter
        @RequiredArgsConstructor
        private enum ServiceType {
            ADMIN_SERVICE(AdminService.class),
            ACCESS_SERVICE(AccessService.class),
            REGISTER_SERVICE(RegisterService.class);

            private final Class<? extends Service> serviceClass;
        }

        public static Service getService(Class<? extends Service> serviceClass) {
            Service service;

            switch (getServiceType(serviceClass)) {
                case ADMIN_SERVICE -> service = serviceClass.cast(new AdminService(new BookRepository(),new UserRepository()));
                case ACCESS_SERVICE -> service = serviceClass.cast(new AccessService(new UserRepository()));
                case REGISTER_SERVICE -> service= serviceClass.cast(new RegisterService(new UserRepository()));
                default -> throw new RuntimeException("There is no such enum");
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
