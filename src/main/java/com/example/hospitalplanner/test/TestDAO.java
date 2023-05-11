package com.example.hospitalplanner.test;

import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAO.UserAuthenticationDAO;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.person.Person;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
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

            testDoctorDAO(connection);

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
