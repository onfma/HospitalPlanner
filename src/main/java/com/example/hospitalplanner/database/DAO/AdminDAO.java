package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.person.Admin;
import com.example.hospitalplanner.entities.person.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private Connection connection;

    public AdminDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Admin> select() throws SQLException {
        String query = "SELECT * FROM ADMINS";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<Admin> adminArrayList = new ArrayList<>();
        while (resultSet.next()) {
            Admin admin = new Admin();
            admin.setEmail(resultSet.getString("EMAIL"));
            adminArrayList.add(admin);
        }

        statement.close();
        resultSet.close();
        return adminArrayList;
    }


    public void insert(Admin admin) throws SQLException {
        String query = "INSERT INTO ADMINS (EMAIL) VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, admin.getEmail());

        statement.executeUpdate();

        statement.close();
    }

    public void delete(String email) throws SQLException {
        String query = "DELETE FROM ADMINS WHERE EMAIL = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        statement.executeUpdate();

        statement.close();
    }

    public boolean existsByEmail(String email) throws SQLException {
        String query = "SELECT COUNT(*) AS cnt FROM ADMINS WHERE EMAIL = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email); // set CNP parameter
        ResultSet resultSet = statement.executeQuery();

        boolean exists = false;
        while (resultSet.next()) {
            if ((resultSet.getInt("cnt") > 1))
                throw new IllegalArgumentException("There are two patients with the same EMAIL! EMAIL = '" + email + "'");
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