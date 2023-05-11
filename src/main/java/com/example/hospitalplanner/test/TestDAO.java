package com.example.hospitalplanner.test;

import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.DoctorsScheduleDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAO.UserAuthenticationDAO;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.person.Person;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {
    public TestDAO(){
        Connection connection = null;

        try {
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass("oracle.jdbc.driver.OracleDriver");
            cpds.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
            cpds.setUser("Arcadia");
            cpds.setPassword("Arcadia");

            connection = cpds.getConnection();

//            testUserAuthenticationDAO(connection);

//            testPatientDAO(connection);

//            testDoctorDAO(connection);

            testDoctorScheduleDAO(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public void testDoctorScheduleDAO(Connection connection) throws SQLException {
        DoctorsScheduleDAO doctorsScheduleDAO = new DoctorsScheduleDAO(connection);

//        DoctorSchedule monday = new DoctorSchedule(5030524268902L, "Monday", LocalTime.of(9,0), LocalTime.of(16,0));
//        DoctorSchedule tuesday = new DoctorSchedule(5030524268902L, "Tuesday", LocalTime.of(9,30), LocalTime.of(16,30));
//        DoctorSchedule wednesday = new DoctorSchedule(5030524268902L, "Wednesday", LocalTime.of(10,0), LocalTime.of(17,0));
//        DoctorSchedule thursday = new DoctorSchedule(5030524268902L, "Thursday", LocalTime.of(10,30), LocalTime.of(17,30));
//        DoctorSchedule friday = new DoctorSchedule(5030524268902L, "Friday", LocalTime.of(10,15), LocalTime.of(17,15));
//
//        doctorsScheduleDAO.insert(monday);
//        doctorsScheduleDAO.insert(tuesday);
//        doctorsScheduleDAO.insert(wednesday);
//        doctorsScheduleDAO.insert(thursday);
//        doctorsScheduleDAO.insert(friday);

        // Delete a specific day from a specific Doctor Schedule
        doctorsScheduleDAO.deleteSpecificDoctorDaySchedule(5030524268902L, "Monday");

        // Weekly Schedule Program for Doctor
        System.out.println(doctorsScheduleDAO.getDoctorSchedule_FullWeek(5030524268902L));

        // Change startTime for a specific day from a Doctor's Schedule
//        doctorsScheduleDAO.setStartTimeSpecificDay(5030524268902L, "Monday", LocalTime.of(9,15));

        // Change endTime for a specific day from a Doctor's Schedule
//        doctorsScheduleDAO.setEndTimeSpecificDay(5030524268902L, "Monday", LocalTime.of(16,15));

        // Get daily schedule program for Doctor
//        System.out.println(doctorsScheduleDAO.getDoctorSchedule_SpecificDay(5030524268902L, "Monday"));

        // Delete all schedule program for a specific doctor

        doctorsScheduleDAO.deleteALLDoctorDaySchedule(5030524268902L);

        // Weekly Schedule Program for Doctor
        System.out.println(doctorsScheduleDAO.getDoctorSchedule_FullWeek(5030524268902L));

        doctorsScheduleDAO.deleteALLDoctorDaySchedule(5030524268902L);
    }

    private void testDoctorDAO(Connection connection) throws SQLException {
        UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(connection);

        // Insert a new Patient
        Patient patient1 = new Patient("mihai.alexandru@yahoo.com", "MihaiAlexandru1");

        userAuthenticationDAO.insert(patient1);

        DoctorDAO doctorDAO = new DoctorDAO(connection);

        // Select all the patients
        List<Doctor> doctorList = new ArrayList<>();

        // Insert a new Patient
        Doctor doctor = new Doctor( 5030524268902L, "Mihai", "Alexandru", 'M', "+441234567890", "mihai.alexandru@yahoo.com","109B, Palace Street");

        doctorDAO.insert(doctor);

        // Select all the patients
        doctorList = doctorDAO.select();

        System.out.println("\nAll doctors (AFTER INSERTION):");
        for (Doctor doctor1 : doctorList) {
            System.out.println("\t" + doctor1);
        }

        doctorDAO.setFirstName(doctor.getCnp(), "Alex");
        doctorDAO.setLastName(doctor.getCnp(), "Alexandru");
        doctorDAO.setPhoneNumber(doctor.getCnp(), "+44-0000-000000");
        doctorDAO.setAddress(doctor.getCnp(), "14, Constitution Hill");

        // Select all the patients
        doctorList = doctorDAO.select();

        System.out.println("\nAll patients (After the changes:");
        for (Doctor doctor1 : doctorList) {
            System.out.println("\t" + doctor1);
        }

        System.out.println("Is there a patient with CNP '5030524268901L'? " + doctorDAO.exists(5030524268901L));
        doctorDAO.delete(5030524268901L);
        System.out.println("Is there a patient with CNP '5030524268901L'? " + doctorDAO.exists(5030524268901L));

    }

    private void testPatientDAO(Connection connection) throws SQLException {
        PatientDAO patientDAO = new PatientDAO(connection);

        // Select all the patients
        List<Patient> patientList = new ArrayList<>();

        // Insert a new Patient
        Patient patient1 = new Patient( 5030524268901L, "Claudiu", "Chichirau", 'M', "+441234567890", "claudiu.chichirau@yahoo.com","109B, Palace Street");

        patientDAO.insert(patient1);

        // Select all the patients
        patientList = patientDAO.select();

        System.out.println("\nAll patients (AFTER INSERTION):");
        for (Patient patient : patientList) {
            System.out.println("\t" + patient);
        }

        patientDAO.setFirstName(5030524268901L, "Alex");
        patientDAO.setLastName(5030524268901L, "Alexandru");
        patientDAO.setPhoneNumber(5030524268901L, "+44-0000-000000");
        patientDAO.setAddress(5030524268901L, "14, Constitution Hill");

        // Select all the patients
        patientList = patientDAO.select();

        System.out.println("\nAll patients (After the changes:");
        for (Patient patient : patientList) {
            System.out.println("\t" + patient);
        }

        System.out.println("Is there a patient with CNP '5030524268901L'? " + patientDAO.exists(5030524268901L));
        patientDAO.delete(5030524268901L);
        System.out.println("Is there a patient with CNP '5030524268901L'? " + patientDAO.exists(5030524268901L));

    }

    public void testUserAuthenticationDAO(Connection connection) throws SQLException {
        UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(connection);

        // Insert a new Patient
        Patient patient1 = new Patient("claudiu.chichirau@yahoo.com", "parolaluiClaudiu1");

        userAuthenticationDAO.insert(patient1);

        System.out.println("\nParola: " + userAuthenticationDAO.getPassword("claudiu.chichirau@yahoo.com"));
        System.out.println("\nSalt: " + userAuthenticationDAO.getSalt("claudiu.chichirau@yahoo.com"));

        userAuthenticationDAO.setPassword("claudiu.chichirau@yahoo.com", "Abcdefghij1");

        System.out.println("\nParola: " + userAuthenticationDAO.getPassword("claudiu.chichirau@yahoo.com"));
        System.out.println("\nSalt: " + userAuthenticationDAO.getSalt("claudiu.chichirau@yahoo.com"));

        // Select all the patients
        List<Person> patientList = new ArrayList<>();

        patientList = userAuthenticationDAO.select();

        System.out.println("\nAll patients (AFTER INSERTION):");
        for (Person person : patientList) {
            System.out.println("\t" + person);
        }

        // Delete the pacient
        userAuthenticationDAO.delete("claudiu.chichirau@yahoo.com");

        patientList = userAuthenticationDAO.select();

        System.out.println("\nAll patients (AFTER DELETION):");
        for (Person person : patientList) {
            System.out.println("\t" + person);
        }
    }
}
