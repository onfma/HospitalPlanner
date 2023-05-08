package com.example.hospitalplanner.test;

import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.person.Person;

public class Test {
    public Test(){
        constructorPerson_EmailPassword();
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
}
