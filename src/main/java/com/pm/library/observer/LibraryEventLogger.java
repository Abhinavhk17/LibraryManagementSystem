package com.pm.library.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryEventLogger implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(LibraryEventLogger.class);

    @Override
    public void update(String event, String details) {
        logger.info("Library Event: {} - {}", event, details);
    }
}
