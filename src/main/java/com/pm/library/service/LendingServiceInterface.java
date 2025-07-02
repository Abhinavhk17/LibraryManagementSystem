package com.pm.library.service;

public interface LendingServiceInterface {
    boolean checkoutBook(String isbn, String patronId);
    boolean returnBook(String isbn);
}
