package com.pm.library.service;

import com.pm.library.model.Patron;
import java.util.List;

public interface PatronServiceInterface {
    boolean registerPatron(Patron patron);
    Patron findPatronById(String patronId);
    List<Patron> getAllPatrons();
}
