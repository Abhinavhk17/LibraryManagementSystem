package com.pm.library.service;

import com.pm.library.model.Book;
import com.pm.library.model.BookStatus;
import com.pm.library.observer.Observer;

import java.util.*;

public class BookService implements BookServiceInterface {
    private final Map<String, Book> booksByIsbn = new HashMap<>();
    private final List<Observer> observers = new ArrayList<>();

    // OBSERVER PATTERN - add/remove observers and notify
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String event, String details) {
        for (Observer observer : observers) {
            observer.update(event, details);
        }
    }

    // STRATEGY PATTERN - enum for different search strategies
    public enum SearchStrategy {
        BY_TITLE, BY_AUTHOR, BY_ISBN, BY_YEAR
    }

    @Override
    public boolean addBook(Book book) {
        if (book == null || book.getIsbn() == null || book.getIsbn().isEmpty()) {
            return false;
        }

        if (booksByIsbn.containsKey(book.getIsbn())) {
            return false; // Book already exists
        }

        booksByIsbn.put(book.getIsbn(), book);
        notifyObservers("BOOK_ADDED",
                String.format("Added book: %s by %s (ISBN: %s)",
                        book.getTitle(), book.getAuthor(), book.getIsbn()));
        return true;
    }

    @Override
    public boolean removeBook(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }

        Book removedBook = booksByIsbn.remove(isbn);
        if (removedBook != null) {
            notifyObservers("BOOK_REMOVED",
                    String.format("Removed book: %s (ISBN: %s)",
                            removedBook.getTitle(), isbn));
            return true;
        }
        return false;
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(booksByIsbn.values());
    }

    @Override
    public List<Book> search(String searchTerm, SearchStrategy strategy) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return new ArrayList<>();
        }

        List<Book> results = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();

        for (Book book : booksByIsbn.values()) {
            switch (strategy) {
                case BY_TITLE:
                    if (book.getTitle().toLowerCase().contains(lowerSearchTerm)) {
                        results.add(book);
                    }
                    break;
                case BY_AUTHOR:
                    if (book.getAuthor().toLowerCase().contains(lowerSearchTerm)) {
                        results.add(book);
                    }
                    break;
                case BY_ISBN:
                    if (book.getIsbn().toLowerCase().contains(lowerSearchTerm)) {
                        results.add(book);
                    }
                    break;
                case BY_YEAR:
                    try {
                        int searchYear = Integer.parseInt(searchTerm);
                        if (book.getPublicationYear() == searchYear) {
                            results.add(book);
                        }
                    } catch (NumberFormatException e) {
                        // Invalid year format, skip this book
                    }
                    break;
            }
        }

        notifyObservers("BOOK_SEARCH",
                String.format("Search by %s for '%s' returned %d results",
                        strategy, searchTerm, results.size()));

        return results;
    }

    public boolean updateBookStatus(String isbn, BookStatus newStatus) {
        Book book = findBookByIsbn(isbn);
        if (book != null) {
            BookStatus oldStatus = book.getStatus();
            book.setStatus(newStatus);
            notifyObservers("BOOK_STATUS_UPDATED",
                    String.format("Book %s status changed from %s to %s",
                            isbn, oldStatus, newStatus));
            return true;
        }
        return false;
    }
}
