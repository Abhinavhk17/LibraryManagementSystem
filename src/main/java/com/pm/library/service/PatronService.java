package com.pm.library.service;

import com.pm.library.model.Patron;
import java.util.*;

public class PatronService implements PatronServiceInterface {
    private final Map<String, Patron> patronsById = new HashMap<>();

    @Override
    public boolean registerPatron(Patron patron) {
        if (patron == null || patron.getPatronId() == null || patron.getPatronId().isEmpty()) {
            return false;
        }

        if (patronsById.containsKey(patron.getPatronId())) {
            return false; // Patron already exists
        }

        patronsById.put(patron.getPatronId(), patron);
        return true;
    }

    @Override
    public Patron findPatronById(String patronId) {
        return patronsById.get(patronId);
    }

    @Override
    public List<Patron> getAllPatrons() {
        return new ArrayList<>(patronsById.values());
    }
}
