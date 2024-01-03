package com.library.backend.services.factories;

import com.library.backend.services.LogInService;
import com.library.backend.services.Service;
import com.library.database.repositories.UserRepository;

public class LogInServiceFactory implements AbstractServiceFactory {
    @Override
    public Service createService() {
        return new LogInService(UserRepository.getInstance());
    }
}
