package com.pm.library.service;

import com.pm.library.model.Book;
import java.util.List;

public interface BookServiceInterface {
    boolean addBook(Book book);
    boolean removeBook(String isbn);
    Book findBookByIsbn(String isbn);
    List<Book> getAllBooks();
    List<Book> search(String searchTerm, BookService.SearchStrategy strategy);
}
