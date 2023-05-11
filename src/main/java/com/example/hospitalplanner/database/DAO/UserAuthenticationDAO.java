package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.person.Person;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UserAuthenticationDAO {

    private Connection connection;

    public UserAuthenticationDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Person> select() throws SQLException {
        String query = "SELECT * FROM USER_AUTHENTICATION";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<Person> personListList = new ArrayList<>();
        while (resultSet.next()) {
            Patient patient = new Patient();
            patient.setEmail(resultSet.getString("EMAIL"));
            patient.setPassword(resultSet.getString("PASSWORD"));
            patient.setSalt(resultSet.getString("SALT"));

            personListList.add(patient);
        }

        statement.close();
        resultSet.close();
        return personListList;
    }


    public void insert(Person person) throws SQLException {
        String query = "INSERT INTO USER_AUTHENTICATION (EMAIL, PASSWORD, SALT) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, person.getEmail());
        statement.setString(2, person.getPassword());
        statement.setString(3, person.getSalt());
        statement.executeUpdate();

        statement.close();
    }

    public String getPassword(String email) throws SQLException {
        String query = "SELECT PASSWORD FROM USER_AUTHENTICATION WHERE EMAIL = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email); // set email parameter
        ResultSet resultSet = statement.executeQuery();

        String password = null;
        while (resultSet.next())
            password = (resultSet.getString("PASSWORD"));

        statement.close();
        resultSet.close();
        return password;
    }

    public void setPassword(String email, String newPassword) throws SQLException {
        // Check password composition
        boolean containsUpperCase = false;
        boolean containsLowerCase = false;
        boolean containsDigit = false;

        for (char c : newPassword.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                containsLowerCase = true;
            } else if (Character.isDigit(c)) {
                containsDigit = true;
            }
        }

        if(newPassword.length() < 8)
            throw new IllegalArgumentException("Password must have at least 8 characters!");

        if (!containsUpperCase || !containsLowerCase || !containsDigit) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, and one digit!");
        }

        // Generate salt
        String salt = generateSalt();

        // Hash password with salt
        String hashedPassword = hashPassword(newPassword, salt);

        String query = "UPDATE USER_AUTHENTICATION SET PASSWORD = ?, SALT = ? WHERE EMAIL = ?";


        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, hashedPassword); // set password parameter
        statement.setString(2, salt); // set password parameter
        statement.setString(3, email); // set email parameter
        statement.executeUpdate();

        statement.close();
    }

    public String getSalt(String email) throws SQLException {
        String query = "SELECT SALT FROM USER_AUTHENTICATION WHERE EMAIL = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email); // set email parameter
        ResultSet resultSet = statement.executeQuery();

        String salt = null;
        while (resultSet.next())
            salt = (resultSet.getString("SALT"));

        statement.close();
        resultSet.close();
        return salt;
    }

    public void delete(String email) throws SQLException {
        String query = "DELETE FROM USER_AUTHENTICATION WHERE EMAIL = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        statement.executeUpdate();

        statement.close();
    }

    private String generateSalt() {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
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
