package com.example.hospitalplanner.test;

import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.person.Person;

import java.util.ArrayList;
import java.util.List;

public class TestPerson {
    public TestPerson(){
//        constructorPerson_EmailPassword();
//        constructorPerson();
//        setMethods();
    }

    private void constructorPerson_EmailPassword() {
        // Email format incorrect
         Patient patient1 = new Patient("@yahoo.com", "Parolatest1");

         Patient patient2 = new Patient("alex@yahoo", "Parolatest1");

         Patient patient3 = new Patient("alex.yahoo.com", "Parolatest1");

        // Password format inccorect

         Patient patient4 = new Patient("alex@yahoo.com", "parolasigura");

         Patient patient5 = new Patient("alex@yahoo.com", "Parolatest");

         Patient patient6 = new Patient("alex@yahoo.com", "parolatest1");

        // Correct format
        Patient patient = new Patient("alex@yahoo.com", "Parolatest1");
    }

    private void constructorPerson(){
        // Invalid CNP format
        Patient patient1 = new Patient(1234567890123344L, "Claudiu", "Chichirau", 'M', "+441234567890", "12, Baker Street");
        Patient patient2 = new Patient(12345L, "Claudiu", "Chichirau", 'M', "+441234567890", "12, Baker Street");

        // Invalid firstName format
        Patient patient3 = new Patient(1234567890123L, "Claudiu@", "Chichirau", 'M', "+441234567890", "12, Baker Street");
        Patient patient4 = new Patient(1234567890123L, "11Claudiu", "Chichirau", 'M', "+441234567890", "12, Baker Street");

        // Invalid lastName format
        Patient patient5 = new Patient(1234567890123L, "Claudiu", "Ch#@ichirau", 'M', "+441234567890", "12, Baker Street");
        Patient patient6 = new Patient(1234567890123L, "Claudiu", "1Chic3hirau", 'M', "+441234567890", "12, Baker Street");

        // Invalid gender format
        Patient patient7 = new Patient(1234567890123L, "Claudiu", "Chichirau", '1', "+441234567890", "12, Baker Street");
        Patient patient8 = new Patient(1234567890123L, "Claudiu", "Chichirau", 'r', "+441234567890", "12, Baker Street");

        // Invalid phoneNumber format
        Patient patient9 = new Patient(1234567890123L, "Claudiu", "Chichirau", 'M', "+44@234567890", "12, Baker Street");
        Patient patient10 = new Patient(1234567890123L, "Claudiu", "Chichirau", 'M', "+4412345678901111", "12, Baker Street");
        Patient patient11 = new Patient(1234567890123L, "Claudiu", "Chichirau", 'M', "+44123", "12, Baker Street");

        // Invalid adress format
        Patient patient12 = new Patient(1234567890123L, "Claudiu", "Chichirau", 'M', "+441234567890", "#@12, Baker Street");

        // Correct format
        Patient patient = new Patient(1234567890123L, "Claudiu", "Chichirau", 'M', "+441234567890", "12, Baker Street");
    }

    private void setMethods(){
        Patient patient = new Patient();

        // Invalid firstName
        patient.setFirstName("Claudiu@");
        patient.setFirstName("11Claudiu");

        // Invalid lastName format
        patient.setLastName("Ch#@ichirau");
        patient.setLastName("1Chic3hirau");

        // Invalid gender format
        patient.setGender('1');
        patient.setGender('r');

        // Invalid phoneNumber format
        patient.setPhoneNumber("+44@234567890");
        patient.setPhoneNumber("+4412345678901111");
        patient.setPhoneNumber("+44123");

        // Invalid adress format
        patient.setAddress("#@12, Baker Street");
    }
}
