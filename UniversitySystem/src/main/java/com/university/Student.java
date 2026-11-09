package com.university;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Data Model for Student Entity with JavaFX Observable Properties.
 * Supports reactive data binding with TableView and UI controls.
 */
public class Student {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty regNumber = new SimpleStringProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty programName = new SimpleStringProperty();

    public Student(int id, String regNumber, String firstName, String lastName) {
        this(id, regNumber, firstName, lastName, regNumber.toLowerCase() + "@university.edu", "General");
    }

    public Student(int id, String regNumber, String firstName, String lastName, String email, String programName) {
        this.id.set(id);
        this.regNumber.set(regNumber);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.email.set(email != null ? email : regNumber.toLowerCase() + "@university.edu");
        this.programName.set(programName != null ? programName : "General");
    }

    // Observable Property Getters (Required for TableView cell value factories)
    public IntegerProperty idProperty() { return id; }
    public StringProperty regNumberProperty() { return regNumber; }
    public StringProperty firstNameProperty() { return firstName; }
    public StringProperty lastNameProperty() { return lastName; }
    public StringProperty emailProperty() { return email; }
    public StringProperty programNameProperty() { return programName; }

    // Standard Value Getters
    public int getId() { return id.get(); }
    public String getRegNumber() { return regNumber.get(); }
    public String getFirstName() { return firstName.get(); }
    public String getLastName() { return lastName.get(); }
    public String getEmail() { return email.get(); }
    public String getProgramName() { return programName.get(); }

    // Standard Value Setters
    public void setId(int id) { this.id.set(id); }
    public void setRegNumber(String regNumber) { this.regNumber.set(regNumber); }
    public void setFirstName(String firstName) { this.firstName.set(firstName); }
    public void setLastName(String lastName) { this.lastName.set(lastName); }
    public void setEmail(String email) { this.email.set(email); }
    public void setProgramName(String programName) { this.programName.set(programName); }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s %s (%s)", getId(), getRegNumber(), getFirstName(), getLastName(), getProgramName());
    }
}
