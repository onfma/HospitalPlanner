package com.example.hospitalplanner.entities.person;

import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAO.UserAuthenticationDAO;
import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.person.Person;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Patient extends Person {

    public Patient() {}

    public Patient(String email, String password){
        super(email, password);
    }
    public Patient(long cnp, String firstName, String lastName, char gender, String phoneNumber, String email, String address) {
        super(cnp, firstName, lastName, gender, phoneNumber, email, address);
    }
    public Patient(long cnp, String firstName, String lastName, char gender, String phoneNumber, String address, List<Appoinments> appoinmentsList) {
        super(cnp, firstName, lastName, gender, phoneNumber, address, appoinmentsList);
    }

    @Override
    public String toString() {
        Connection connection = null;

        try {
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass("oracle.jdbc.driver.OracleDriver");
            cpds.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
            cpds.setUser("Arcadia");
            cpds.setPassword("Arcadia");

            connection = cpds.getConnection();

            PatientDAO patientDAO = new PatientDAO(connection);
            UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(connection);

            StringBuilder sb = new StringBuilder();

            firstName = patientDAO.getFirstName(cnp);
            lastName = patientDAO.getLastName(cnp);
            gender = patientDAO.getGender(cnp);
            phoneNumber = patientDAO.getPhoneNumber(cnp);
            email = patientDAO.getEmail(cnp);
            password = userAuthenticationDAO.getPassword(patientDAO.getEmail(cnp));
            salt = userAuthenticationDAO.getSalt(patientDAO.getEmail(cnp));

            sb.append("Patient");
            if (cnp != 0)
                sb.append("\n\t\t- CNP = '" + cnp + "'");
            if (firstName != null)
                sb.append("\n\t\t- FIRST_NAME = '" + firstName + '\'');
            if (lastName != null)
                sb.append("\n\t\t- LAST_NAME = '" + lastName + '\'');
            if (gender != '\0')
                sb.append("\n\t\t- GENDER = '" + gender + '\'');
            if (phoneNumber != null)
                sb.append("\n\t\t- PHONE_NUMBER= '" + phoneNumber + '\'');
            if (address != null)
                sb.append("\n\t\t- ADDRESS = '" + address + '\'');
            if (email != null)
                sb.append("\n\t\t- EMAIL = '" + email + '\'');
            if (password != null)
                sb.append("\n\t\t- PASSWORD = '" + password + '\'');
            if (salt != null)
                sb.append("\n\t\t- SALT = '" + salt + '\'');

            return sb.toString();
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
