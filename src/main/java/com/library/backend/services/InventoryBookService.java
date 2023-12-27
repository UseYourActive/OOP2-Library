package com.library.backend.services;

import com.google.common.base.Preconditions;
import com.library.database.entities.BookInventory;
import com.library.database.repositories.BookInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InventoryBookService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(InventoryBookService.class);
    private final BookInventoryRepository bookInventoryRepository;

    public InventoryBookService(BookInventoryRepository bookInventoryRepository) {
        this.bookInventoryRepository = Preconditions.checkNotNull(bookInventoryRepository,"BookInventoryRepository cannot be null");
    }

    public List<BookInventory> getAllBookInventories() {
        List<BookInventory> inventories = bookInventoryRepository.findAll();
        logger.info("Retrieved {} book inventories from the repository.", inventories.size());
        return inventories;
    }
}
