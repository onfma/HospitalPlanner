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

    public void delete(Patient patient) throws SQLException {
        String query = "DELETE FROM pacients WHERE CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, patient.getCnp());
        statement.executeUpdate();

        statement.close();
    }
//
//    public int getAlbumId(String albumName) throws SQLException {
//        PreparedStatement statement = connection.prepareStatement("SELECT id FROM artists WHERE name = ?");
//        statement.setString(1, albumName);
//        ResultSet resultSet = statement.executeQuery();
//
//        if (resultSet.next()) {
//            int rezult = resultSet.getInt("id");
//
//            statement.close();
//            resultSet.close();
//
//            return rezult;
//        } else {
//            statement.close();
//            resultSet.close();
//            return -1; // artist not found
//        }
//    }
//
//    public int getMaxAlbumId() throws SQLException {
//        String query = "SELECT MAX(id) AS max_id FROM albums";
//        PreparedStatement statement = connection.prepareStatement(query);
//        ResultSet resultSet = statement.executeQuery();
//
//        if (resultSet.next()) {
//            int rezult = resultSet.getInt("max_id");
//
//            statement.close();
//            resultSet.close();
//
//            return rezult;
//        }
//
//        statement.close();
//        resultSet.close();
//        return 0;
//    }
}
