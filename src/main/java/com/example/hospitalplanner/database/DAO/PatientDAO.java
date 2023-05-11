package com.example.hospitalplanner.database.DAO;


import com.example.hospitalplanner.entities.person.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private Connection connection;

    public PatientDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Patient> select() throws SQLException {
        String query = "SELECT * FROM pacients";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<Patient> patientList = new ArrayList<>();
        while (resultSet.next()) {
            Patient patient = new Patient();
            patient.setCnp(resultSet.getLong("CNP"));
            patient.setFirstName(resultSet.getString("FIRST_NAME"));
            patient.setLastName(resultSet.getString("LAST_NAME"));
            patient.setGender(resultSet.getString("GENDER").charAt(0));
            patient.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
            patient.setEmail(resultSet.getString("EMAIL"));
            patient.setAddress(resultSet.getString("ADDRESS"));

            patientList.add(patient);
        }

        statement.close();
        resultSet.close();
        return patientList;
    }


    public void insert(Patient patient) throws SQLException {
        String query = "INSERT INTO pacients (CNP, FIRST_NAME, LAST_NAME, GENDER, PHONE_NUMBER, EMAIL, ADDRESS) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, patient.getCnp());
        statement.setString(2, patient.getFirstName());
        statement.setString(3, patient.getLastName());
        statement.setString(4, String.valueOf(patient.getGender()));
        statement.setString(5, patient.getPhoneNumber());
        statement.setString(6, patient.getEmail());
        statement.setString(7, patient.getAddress());

        statement.executeUpdate();

        statement.close();
    }

    public void delete(long CNP) throws SQLException {
        String query = "DELETE FROM pacients WHERE CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, CNP);
        statement.executeUpdate();

        statement.close();
    }

    public String getFirstName(long CNP) throws SQLException {
        String query = "SELECT FIRST_NAME FROM PACIENTS WHERE CNP = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, CNP); // set CNP parameter
        ResultSet resultSet = statement.executeQuery();

        String firstName = null;
        while (resultSet.next())
            firstName = (resultSet.getString("FIRST_NAME"));

        statement.close();
        resultSet.close();
        return firstName;
    }

    public void setFirstName(long CNP, String newFirstName) throws SQLException {
        if (!newFirstName.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Invalid first name: First name should contain only letters.");

        String query = "UPDATE PACIENTS SET FIRST_NAME = ? WHERE CNP = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newFirstName); // set FirstName parameter
        statement.setLong(2, CNP); // set CNP parameter
        statement.executeUpdate();

        statement.close();
    }

    public String getLastName(long CNP) throws SQLException {
        String query = "SELECT LAST_NAME FROM PACIENTS WHERE CNP = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, CNP); // set CNP parameter
        ResultSet resultSet = statement.executeQuery();

        String lastName = null;
        while (resultSet.next())
            lastName = (resultSet.getString("LAST_NAME"));

        statement.close();
        resultSet.close();
        return lastName;
    }

    public void setLastName(long CNP, String newLastName) throws SQLException {
        if (!newLastName.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Invalid last name: Last name should contain only letters.");

        String query = "UPDATE PACIENTS SET LAST_NAME = ? WHERE CNP = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newLastName); // set LastName parameter
        statement.setLong(2, CNP); // set CNP parameter
        statement.executeUpdate();

        statement.close();
    }

    public Character getGender(long CNP) throws SQLException {
        String query = "SELECT GENDER FROM PACIENTS WHERE CNP = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, CNP); // set CNP parameter
        ResultSet resultSet = statement.executeQuery();

        char gender = '\0';
        while (resultSet.next())
            gender = (resultSet.getString("GENDER").charAt(0));

        statement.close();
        resultSet.close();
        return gender;
    }

    public String getPhoneNumber(long CNP) throws SQLException {
        String query = "SELECT PHONE_NUMBER FROM PACIENTS WHERE CNP = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, CNP); // set CNP parameter
        ResultSet resultSet = statement.executeQuery();

        String phoneNumber = null;
        while (resultSet.next())
            phoneNumber = (resultSet.getString("PHONE_NUMBER"));

        statement.close();
        resultSet.close();
        return phoneNumber;
    }

    public void setPhoneNumber(long CNP, String newPhoneNumber) throws SQLException {
        if (!newPhoneNumber.matches("\\+44-\\d{4}-\\d{6}") && !newPhoneNumber.matches("\\+44\\d{10}"))
            throw new IllegalArgumentException("Invalid phone number: Phone number should be in the format '+44-xxxx-xxxxxx' or '+44xxxxxxxxxx' and should contains only digits.");

        String query = "UPDATE PACIENTS SET PHONE_NUMBER = ? WHERE CNP = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newPhoneNumber); // set PhoneNumber parameter
        statement.setLong(2, CNP); // set CNP parameter
        statement.executeUpdate();

        statement.close();
    }

    public String getEmail(long CNP) throws SQLException {
        String query = "SELECT EMAIL FROM PACIENTS WHERE CNP = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, CNP); // set CNP parameter
        ResultSet resultSet = statement.executeQuery();

        String EMAIL = null;
        while (resultSet.next())
            EMAIL = (resultSet.getString("EMAIL"));

        statement.close();
        resultSet.close();
        return EMAIL;
    }

    public String getAddress(long CNP) throws SQLException {
        String query = "SELECT ADDRESS FROM PACIENTS WHERE CNP = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, CNP); // set CNP parameter
        ResultSet resultSet = statement.executeQuery();

        String ADDRESS = null;
        while (resultSet.next())
            ADDRESS = (resultSet.getString("ADDRESS"));

        statement.close();
        resultSet.close();
        return ADDRESS;
    }

    public void setAddress(long CNP, String newAddress) throws SQLException {
        if (!newAddress.matches("[a-zA-Z0-9,. ;]+"))
            throw new IllegalArgumentException("Invalid address: Address should contain only letters, digits, or the following characters: [,.;]");

        String query = "UPDATE PACIENTS SET ADDRESS = ? WHERE CNP = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newAddress); // set FirstName parameter
        statement.setLong(2, CNP); // set CNP parameter
        statement.executeUpdate();

        statement.close();
    }

    public boolean exists(long CNP) throws SQLException {
        String query = "SELECT COUNT(*) AS cnt FROM PACIENTS WHERE CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, CNP); // set CNP parameter
        ResultSet resultSet = statement.executeQuery();

        boolean exists = false;
        while (resultSet.next()) {
            if ((resultSet.getInt("cnt") > 1))
                throw new IllegalArgumentException("There are two patients with the same CNP! CNP = '" + CNP + "'");
            else if((resultSet.getInt("cnt") == 1))
                exists = true;
            else if((resultSet.getInt("cnt") == 0))
                exists = false;
        }

        statement.close();
        resultSet.close();
        return exists;
    }


}
