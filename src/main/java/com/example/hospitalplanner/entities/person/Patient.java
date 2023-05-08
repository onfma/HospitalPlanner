package com.example.hospitalplanner.entities.person;

import com.example.hospitalplanner.entities.person.Person;

public class Patient extends Person {

    public Patient() {}

    public Patient(String email, String password){
        super(email, password);
    }
    public Patient(int cnp, String firstName, String lastName, String gender, String phoneNumber, String address) {
        super(cnp, firstName, lastName, gender, phoneNumber, address);
    }
}
