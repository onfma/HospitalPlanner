package com.example.hospitalplanner;

import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.test.TestPerson;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TestPerson test = new TestPerson();

        Connection connection = null;

        try {
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass("oracle.jdbc.driver.OracleDriver");
            cpds.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
            cpds.setUser("Arcadia");
            cpds.setPassword("Arcadia");

            connection = cpds.getConnection();

            PatientDAO patientDAO = new PatientDAO(connection);

            List<Patient> patientList = new ArrayList<>();

            patientList = patientDAO.select();

            System.out.println("All patients:");
            for (Patient patient : patientList) {
                System.out.println("\t" + patient);
            }

            // Patient patient1 = new Patient(1234567890123L, "Claudiu", "Chichirau", 'M', "+441234567890", "12, Baker Street");

            connection = cpds.getConnection();
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
}
