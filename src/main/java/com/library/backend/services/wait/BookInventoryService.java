package com.library.backend.services.wait;

import com.google.common.base.Preconditions;
import com.library.backend.services.Service;
import com.library.database.entities.BookInventory;
import com.library.database.repositories.BookInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Supplier;

public class BookInventoryService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(BookInventoryService.class);
    private final BookInventoryRepository bookInventoryRepository;

    public BookInventoryService(BookInventoryRepository bookInventoryRepository) {
        this.bookInventoryRepository = Preconditions.checkNotNull(bookInventoryRepository, "BookInventoryRepository cannot be null");
    }

    public List<BookInventory> getAllBookInventories() {
        List<BookInventory> inventories = bookInventoryRepository.findAll();
        logger.info("Retrieved {} book inventories from the repository.", inventories.size());
        return inventories;
    }

    public void saveInventory(BookInventory bookInventory) {
        performRepositoryOperation(() -> bookInventoryRepository.save(bookInventory), "saved", "");
    }

    public void removeInventory(BookInventory bookInventory) {
        performRepositoryOperation(() -> bookInventoryRepository.delete(bookInventory), "deleted", "");
    }

    private void logEntityRetrieval(String entityName, int size) {
        logger.info("Retrieved {} {}: {}", size, entityName, (size == 1 ? "entity" : "entities"));
    }

    private <T> void performRepositoryOperation(Supplier<T> repositoryOperation, String action, String entityName) {
        T result = repositoryOperation.get();
        if (result != null) {
            logger.info("{} {} successfully: {}", entityName, action, entityName);
        } else {
            logger.error("Failed to {} {}: {}", action, entityName, entityName);
        }
    }

}
