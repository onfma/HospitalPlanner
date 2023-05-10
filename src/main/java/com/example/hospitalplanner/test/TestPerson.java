package com.example.hospitalplanner.test;

import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.person.Admin;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class TestPerson {
    public TestPerson(){
//        constructorPerson_EmailPassword();
//        constructorPerson();
//        setMethods();
//        adminRights();
        passwordSaltHash();
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

    public void adminRights() {
        // Patient list
        List<Patient> patientList = new ArrayList<>();

        Patient patient1 = new Patient(1234567890123L, "Claudiu", "Chichirau", 'M', "+441234567890", "12, Baker Street");
        Patient patient2 = new Patient(1234567890124L, "Mihai", "Zoe", 'M', "+441234567891", "13, Baker Street");
        Patient patient3 = new Patient(1234567890125L, "Antonia", "Popovici", 'F', "+441234567892", "14, Baker Street");

        patientList.add(patient1);
        patientList.add(patient2);
        patientList.add(patient3);

        // Doctor list
        List<Doctor> doctorList = new ArrayList<>();

        Doctor doctor1 = new Doctor(1234567890126L, "Alex", "Alexandru", 'M', "+441234467890", "15, Baker Street");
        Doctor doctor2 = new Doctor(1234567890127L, "Naria", "Onofrei", 'F', "+441264467890", "16, Baker Street");
        Doctor doctor3 = new Doctor(1234567890128L, "Narian", "Onofrei", 'M', "+441264587890", "17, Baker Street");

        doctorList.add(doctor1);
        doctorList.add(doctor2);
        doctorList.add(doctor3);

        // Cabinet list
        List<Cabinet> cabinetList = new ArrayList<>();

        Cabinet cabinet1 = new Cabinet(1, "Pediatrics");
        Cabinet cabinet2 = new Cabinet(2, "Neurosurgery");
        Cabinet cabinet3 = new Cabinet(3, "Oncology");

        cabinetList.add(cabinet1);
        cabinetList.add(cabinet2);
        cabinetList.add(cabinet3);

        // CabinetSchedule list
        List<CabinetSchedule> cabinetSchedulesList = new ArrayList<>();

        CabinetSchedule cabinetSchedule1 = new CabinetSchedule(1, "Monday",  LocalTime.of(10,0),  LocalTime.of(17,0));
        CabinetSchedule cabinetSchedule2 = new CabinetSchedule(2, "Friday",  LocalTime.of(10,30),  LocalTime.of(13,0));
        CabinetSchedule cabinetSchedule3 = new CabinetSchedule(3, "Monday",  LocalTime.of(12,0),  LocalTime.of(19,30));

        // DoctorSchedule list
        List<DoctorSchedule> doctorScheduleList = new ArrayList<>();

        DoctorSchedule doctorSchedule1 = new DoctorSchedule(1234567890126L, "Monday", LocalTime.of(10,0),  LocalTime.of(17,0));
        DoctorSchedule doctorSchedule2 = new DoctorSchedule(1234567890127L, "Tuesday", LocalTime.of(10,0),  LocalTime.of(17,0));
        DoctorSchedule doctorSchedule3 = new DoctorSchedule(1234567890128L, "Wednesday", LocalTime.of(10,0),  LocalTime.of(17,0));

        // AppoinmentsList list
        List<Appoinments> appoinmentsList = new ArrayList<>();

        Appoinments appoinment1 = new Appoinments(1, 1, 1234567890126L , 1234567890123L, LocalDateTime.of(2023, 5, 12, 14, 30));
        Appoinments appoinment2 = new Appoinments(2, 2, 1234567890127L , 1234567890123L, LocalDateTime.of(2023, 5, 13, 14, 30));
        Appoinments appoinment3 = new Appoinments(1, 1, 1234567890128L , 1234567890123L, LocalDateTime.of(2023, 5, 14, 14, 30));

        appoinmentsList.add(appoinment1);
        appoinmentsList.add(appoinment2);
        appoinmentsList.add(appoinment3);

        Admin admin = new Admin(patientList, doctorList, cabinetList, cabinetSchedulesList, doctorScheduleList, appoinmentsList);

        // test if I can edit a patient's data
        List<Patient> newPatientList = new ArrayList<>();
        newPatientList = admin.getPatientList();

        Patient newPatient = new Patient();
        newPatient=newPatientList.get(0);

        newPatient.setGender('F');

        newPatientList.add(newPatient);
    }

    public void passwordSaltHash() {
        // The same password saved different in the database
        Patient patient1 = new Patient("alex@yahoo.com", "Parolatest1");
//        System.out.println("Password: " + patient1.getPassword() + ", salt: " + patient1.getSalt());

        Patient patient2 = new Patient("alexandru@yahoo.com", "Parolatest1");
//        System.out.println("Password: " + patient2.getPassword() + ", salt: " + patient2.getSalt());

        // test to see if the correct password is identical to the one saved in the database or a wrong
        // password is different from the one saved in the database

        // Correct hash password with salt
        String correctHashedPassword = hashPassword("Parolatest1", patient1.getSalt());

        if (!correctHashedPassword.equals(patient1.getPassword()))
            throw new IllegalArgumentException("The password provided should be correct, but isn't!");

        // Incorrect hash password with salt
        String incorrectHashedPassword = hashPassword("hello@-13.", patient2.getSalt());

        if (incorrectHashedPassword.equals(patient2.getPassword()))
            throw new IllegalArgumentException("The password provided should be incorrect, but is!");
    }

    private String hashPassword(String password, String salt) {
        // Hash password with salt
        String passwordAndSalt = password + salt;
        String hashedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPasswordBytes = md.digest(passwordAndSalt.getBytes());
            hashedPassword = Base64.getEncoder().encodeToString(hashedPasswordBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }
}
