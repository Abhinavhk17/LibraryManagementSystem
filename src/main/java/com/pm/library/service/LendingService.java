package com.pm.library.service;

import com.pm.library.model.Book;
import com.pm.library.model.BookStatus;
import com.pm.library.model.Patron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LendingService implements LendingServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(LendingService.class);

    private final BookService bookService;
    private final PatronService patronService;

    public LendingService(BookService bookService, PatronService patronService) {
        this.bookService = bookService;
        this.patronService = patronService;
    }

    @Override
    public boolean checkoutBook(String isbn, String patronId) {
        if (isbn == null || patronId == null || isbn.isEmpty() || patronId.isEmpty()) {
            logger.warn("Invalid checkout attempt: isbn={}, patronId={}", isbn, patronId);
            return false;
        }

        Book book = bookService.findBookByIsbn(isbn);
        if (book == null) {
            logger.warn("Book not found for checkout: {}", isbn);
            return false;
        }

        if (book.getStatus() != BookStatus.AVAILABLE) {
            logger.warn("Book {} is not available for checkout. Current status: {}", isbn, book.getStatus());
            return false;
        }

        Patron patron = patronService.findPatronById(patronId);
        if (patron == null) {
            logger.warn("Patron not found: {}", patronId);
            return false;
        }

        boolean statusUpdated = bookService.updateBookStatus(isbn, BookStatus.CHECKED_OUT);
        if (statusUpdated) {
            logger.info("Book '{}' successfully checked out to patron '{}'", book.getTitle(), patron.getName());
            return true;
        }

        return false;
    }

    @Override
    public boolean returnBook(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            logger.warn("Invalid return attempt: isbn={}", isbn);
            return false;
        }

        Book book = bookService.findBookByIsbn(isbn);
        if (book == null) {
            logger.warn("Book not found for return: {}", isbn);
            return false;
        }

        if (book.getStatus() != BookStatus.CHECKED_OUT) {
            logger.warn("Book {} is not checked out. Current status: {}", isbn, book.getStatus());
            return false;
        }

        boolean statusUpdated = bookService.updateBookStatus(isbn, BookStatus.AVAILABLE);
        if (statusUpdated) {
            logger.info("Book '{}' successfully returned", book.getTitle());
            return true;
        }

        return false;
    }
}
