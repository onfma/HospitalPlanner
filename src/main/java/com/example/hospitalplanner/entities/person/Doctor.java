package com.example.hospitalplanner.entities.person;

import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAO.UserAuthenticationDAO;
import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.schedule.*;
import com.example.hospitalplanner.entities.person.Person;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Doctor extends Person {
    private List<Cabinet> specialties;
    private List<DoctorSchedule> doctorSchedules;
    private List<Appoinments> appoinmentsList;

    public Doctor() {}

    public Doctor(String email, String password){
        super(email, password);
    }
    public Doctor(long cnp, String firstName, String lastName, char gender, String phoneNumber, String email, String address) {
        super(cnp, firstName, lastName, gender, phoneNumber, email,address);
    }

    public List<Cabinet> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Cabinet> specialties) {
        this.specialties = specialties;
    }

    public void addSpecialty(Cabinet specialty){
        if (specialties.contains(specialty))
            throw new IllegalArgumentException("This specialty already exists in the specialties list");

        specialties.add(specialty);
    }

    public void removeSpecialty(Cabinet specialty){
        specialties.remove(specialty);
    }

    public List<DoctorSchedule> getDoctorSchedule() {
        return doctorSchedules;
    }

    public void setDoctorSchedule(List<DoctorSchedule> doctorSchedules) {
        this.doctorSchedules = doctorSchedules;
    }

    public void addDoctorSchedule(DoctorSchedule doctorSchedule){
        if (doctorSchedules.contains(doctorSchedule))
            throw new IllegalArgumentException("This doctorSchedule already exists in the doctorSchedules list");

        doctorSchedules.add(doctorSchedule);
    }

    public void removeDoctorSchedule(DoctorSchedule doctorSchedule){
        doctorSchedules.remove(doctorSchedule);
    }

    public List<Appoinments> getAppoinmentsList() {
        return appoinmentsList;
    }

    @Override
    public void setAppoinments(List<Appoinments> appoinments) {
        super.setAppoinments(appoinments);
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

            DoctorDAO doctorDAO= new DoctorDAO(connection);
            UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(connection);

            StringBuilder sb = new StringBuilder();

            firstName = doctorDAO.getFirstName(cnp);
            lastName = doctorDAO.getLastName(cnp);
            gender = doctorDAO.getGender(cnp);
            phoneNumber = doctorDAO.getPhoneNumber(cnp);
            email = doctorDAO.getEmail(cnp);
            password = userAuthenticationDAO.getPassword(doctorDAO.getEmail(cnp));
            salt = userAuthenticationDAO.getSalt(doctorDAO.getEmail(cnp));

            sb.append("Doctor");
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
