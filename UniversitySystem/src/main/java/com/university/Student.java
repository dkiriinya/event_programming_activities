package com.university;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Data Model for Student Entity with JavaFX Observable Properties.
 */
public class Student {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty regNumber = new SimpleStringProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();

    public Student(int id, String regNumber, String firstName, String lastName) {
        this.id.set(id);
        this.regNumber.set(regNumber);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty regNumberProperty() { return regNumber; }
    public StringProperty firstNameProperty() { return firstName; }
    public StringProperty lastNameProperty() { return lastName; }

    public int getId() { return id.get(); }
    public String getRegNumber() { return regNumber.get(); }
    public String getFirstName() { return firstName.get(); }
    public String getLastName() { return lastName.get(); }
}
