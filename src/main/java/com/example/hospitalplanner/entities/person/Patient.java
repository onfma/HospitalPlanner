package com.example.hospitalplanner.entities.person;

import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.person.Person;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;

import java.util.List;

public class Patient extends Person {

    public Patient() {}

    public Patient(String email, String password){
        super(email, password);
    }
    public Patient(long cnp, String firstName, String lastName, char gender, String phoneNumber, String address) {
        super(cnp, firstName, lastName, gender, phoneNumber, address);
    }
    public Patient(long cnp, String firstName, String lastName, char gender, String phoneNumber, String address, List<Appoinments> appoinmentsList) {
        super(cnp, firstName, lastName, gender, phoneNumber, address, appoinmentsList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Patient (" +
                "CNP = '" + cnp + "')" +
                "\n\t\t- FIRST_NAME = '" + firstName + '\'' +
                "\n\t\t- LAST_NAME = '" + lastName + '\'' +
                "\n\t\t- GENDER = '" + gender + '\'' +
                "\n\t\t- PHONE_NUMBER= '" + phoneNumber + '\'' +
                "\n\t\t- EMAIL = '" + email + '\'' +
                "\n\t\t- ADDRESS = '" + address + '\'' +
                "\n"
        );

        return sb.toString();
    }
}
