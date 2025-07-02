package com.pm.library.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Simple observer that tracks book inventory changes
public class InventoryObserver implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(InventoryObserver.class);
    private int totalBooks = 0;

    @Override
    public void update(String event, String details) {
        if (event.contains("BOOK_ADDED")) {
            totalBooks++;
            logger.info("Inventory Update: Total books now: {}", totalBooks);
        } else if (event.contains("BOOK_REMOVED")) {
            totalBooks--;
            logger.info("Inventory Update: Total books now: {}", totalBooks);
        }
        logger.debug("Inventory tracking: {} - {}", event, details);
    }

    public int getTotalBooks() {
        return totalBooks;
    }
}
