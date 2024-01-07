package com.library.backend.services.factories;

import com.library.backend.services.OperatorService;
import com.library.backend.services.Service;
import com.library.database.repositories.*;

public class OperatorServiceFactory implements AbstractServiceFactory {
    @Override
    public Service createService() {
        return new OperatorService(
                BookRepository.getInstance(),
                ReaderRepository.getInstance(),
                BookInventoryRepository.getInstance(),
                BookFormRepository.getInstance(),
                EventNotificationRepository.getInstance(),
                ReaderRatingRepository.getInstance()
        );
    }
}
