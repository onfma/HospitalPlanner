package com.example.hospitalplanner.entities.person;

import com.example.hospitalplanner.entities.person.Person;

public class Patient extends Person {

    public Patient() {}

    public Patient(String email, String password){
        super(email, password);
    }
    public Patient(long cnp, String firstName, String lastName, char gender, String phoneNumber, String address) {
        super(cnp, firstName, lastName, gender, phoneNumber, address);
    }

    @Override
    public String toString() {
        return "Patient {" +
                "CNP = " + cnp +
                ", FIRST_NAME = '" + firstName + '\'' +
                ", LAST_NAME = " + lastName +
                ", GENDER = " + gender +
                ", PHONE_NUMBER= " + phoneNumber +
                ", EMAIL = " + email +
                ", ADDRESS = " + address +
                '}';
    }
}
