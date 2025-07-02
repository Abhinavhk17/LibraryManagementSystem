package com.pm.library;

import com.pm.library.model.Book;
import com.pm.library.model.Patron;
import com.pm.library.service.BookService;
import com.pm.library.service.PatronService;
import com.pm.library.service.LendingService;
import com.pm.library.observer.LibraryEventLogger;
import com.pm.library.observer.InventoryObserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LibraryApplication {

    private static final Logger logger = LoggerFactory.getLogger(LibraryApplication.class);

    public static void main(String[] args) {
        logger.info("=== Library Management System  ===");

        // Initialize services
        BookService bookService = new BookService();
        PatronService patronService = new PatronService();
        LendingService lendingService = new LendingService(bookService, patronService);

        // OBSERVER PATTERN - Add observers to watch library events
        LibraryEventLogger eventLogger = new LibraryEventLogger();
        InventoryObserver inventoryTracker = new InventoryObserver();

        bookService.addObserver(eventLogger);
        bookService.addObserver(inventoryTracker);

        // Demonstrate both patterns
        demonstrateObserverPattern(bookService);
        demonstrateStrategyPattern(bookService);
        demonstrateLendingWithObserver(lendingService, patronService);

    }

    private static void demonstrateObserverPattern(BookService bookService) {
        logger.info("--- OBSERVER PATTERN DEMONSTRATION ---");

        // Adding books will trigger observer notifications
        Book book1 = new Book("Effective Java", "Joshua Bloch", "978-0134685991", 2018);
        Book book2 = new Book("Clean Code", "Robert C. Martin", "978-0132350884", 2008);
        Book book3 = new Book("Design Patterns", "Gang of Four", "978-0201633612", 1994);

        logger.info("Adding books (watch for observer notifications):");
        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);

        logger.info("Total books in system: {}", bookService.getAllBooks().size());
    }

    private static void demonstrateStrategyPattern(BookService bookService) {
        logger.info("--- STRATEGY PATTERN DEMONSTRATION ---");

        // Different search strategies
        logger.info("Searching by different strategies:");

        List<Book> titleResults = bookService.search("Java", BookService.SearchStrategy.BY_TITLE);
        logger.info("Search by title 'Java': {} results", titleResults.size());

        List<Book> authorResults = bookService.search("Martin", BookService.SearchStrategy.BY_AUTHOR);
        logger.info("Search by author 'Martin': {} results", authorResults.size());

        List<Book> isbnResults = bookService.search("978-0134685991", BookService.SearchStrategy.BY_ISBN);
        logger.info("Search by ISBN '978-0134685991': {} results", isbnResults.size());

        List<Book> yearResults = bookService.search("2018", BookService.SearchStrategy.BY_YEAR);
        logger.info("Search by year '2018': {} results", yearResults.size());
    }

    private static void demonstrateLendingWithObserver(LendingService lendingService, PatronService patronService) {
        logger.info("--- LENDING OPERATIONS WITH OBSERVER NOTIFICATIONS ---");

        // Register patrons
        Patron patron1 = new Patron("P001", "Alice Johnson", "alice@email.com", "555-0101");
        Patron patron2 = new Patron("P002", "Bob Smith", "bob@email.com", "555-0102");

        patronService.registerPatron(patron1);
        patronService.registerPatron(patron2);
        logger.info("Registered {} patrons", patronService.getAllPatrons().size());

        // Demonstrate lending operations (which will trigger observer notifications)
        logger.info("Performing lending operations:");

        boolean checkout1 = lendingService.checkoutBook("978-0134685991", "P001");
        logger.info("Checkout attempt 1: {}", checkout1 ? "SUCCESS" : "FAILED");

        boolean checkout2 = lendingService.checkoutBook("978-0132350884", "P002");
        logger.info("Checkout attempt 2: {}", checkout2 ? "SUCCESS" : "FAILED");

        // Try to checkout an already checked out book
        boolean checkout3 = lendingService.checkoutBook("978-0134685991", "P002");
        logger.info("Checkout attempt 3 (should fail): {}", checkout3 ? "SUCCESS" : "FAILED");

        // Return books
        boolean return1 = lendingService.returnBook("978-0134685991");
        logger.info("Return attempt 1: {}", return1 ? "SUCCESS" : "FAILED");

        boolean return2 = lendingService.returnBook("978-0132350884");
        logger.info("Return attempt 2: {}", return2 ? "SUCCESS" : "FAILED");
    }
}
