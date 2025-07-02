package com.pm.library.model;

import java.util.Objects;

public class Patron {
    private String patronId;
    private String name;
    private String email;
    private String phoneNumber;

    public Patron(String patronId, String name, String email, String phoneNumber) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getPatronId() { return patronId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    // Setters
    public void setPatronId(String patronId) { this.patronId = patronId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patron patron = (Patron) o;
        return Objects.equals(patronId, patron.patronId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patronId);
    }

    @Override
    public String toString() {
        return String.format("Patron[id='%s', name='%s', email='%s', phone='%s']",
                patronId, name, email, phoneNumber);
    }
}
