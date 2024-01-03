package com.library.backend.services.factories;

import com.library.backend.services.Service;
import com.library.backend.services.BookInventoryService;
import com.library.database.repositories.BookInventoryRepository;

public class BookInventoryServiceFactory implements AbstractServiceFactory {
    @Override
    public Service createService() {
        return new BookInventoryService(BookInventoryRepository.getInstance());
    }
}
