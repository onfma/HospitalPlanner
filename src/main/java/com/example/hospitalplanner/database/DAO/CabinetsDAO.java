package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CabinetsDAO {
    private Connection connection;

    public CabinetsDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Cabinet> select() throws SQLException {
        String query = "SELECT * FROM CABINETS";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<Cabinet> cabinetList = new ArrayList<>();
        while (resultSet.next()) {
            Cabinet cabinet = new Cabinet();
            cabinet.setId(resultSet.getInt("ID"));
            cabinet.setSpecialtyName(resultSet.getString("SPECIALTY"));

            cabinetList.add(cabinet);
        }

        statement.close();
        resultSet.close();
        return cabinetList;
    }


    public void insert(Cabinet cabinet) throws SQLException {
        String query = "INSERT INTO CABINETS (ID, SPECIALTY) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, cabinet.getId());
        statement.setString(2, cabinet.getSpecialtyName());
        statement.executeUpdate();

        statement.close();
    }

    public void delete(Cabinet cabinet) throws SQLException {
        String query = "DELETE FROM CABINETS WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cabinet.getId()); // set ID parameter
        statement.executeUpdate();

        statement.close();
    }
}
