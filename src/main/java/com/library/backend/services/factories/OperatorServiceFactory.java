package com.library.backend.services.factories;

import com.library.backend.services.OperatorService;
import com.library.backend.services.Service;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.ReaderRepository;

public class OperatorServiceFactory implements AbstractServiceFactory {
    @Override
    public Service createService() {
        return new OperatorService(
                BookRepository.getInstance(),
                ReaderRepository.getInstance(),
                BookInventoryRepository.getInstance(),
                BookFormRepository.getInstance()
        );
    }
}
