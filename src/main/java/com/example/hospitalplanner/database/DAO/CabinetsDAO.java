package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public String selectJSON() throws SQLException {
        String query = "SELECT * FROM CABINETS";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", resultSet.getInt("ID"));
            jsonObject.put("specialityName", resultSet.getString("SPECIALTY"));

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }


    public void insert(Cabinet cabinet) throws SQLException {
        String query = "INSERT INTO CABINETS (ID, SPECIALTY) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, cabinet.getId());
        statement.setString(2, cabinet.getSpecialtyName());
        statement.executeUpdate();

        statement.close();
    }

    public String getSpecialityName(int cabinetID) throws SQLException {
        String query = "SELECT SPECIALTY FROM CABINETS WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, cabinetID); // set cabinetID parameter
        ResultSet resultSet = statement.executeQuery();

        String specialityName = null;
        while (resultSet.next())
            specialityName = (resultSet.getString("SPECIALTY"));

        statement.close();
        resultSet.close();
        return specialityName;
    }

    public void delete(int cabinetID) throws SQLException {
        String query = "DELETE FROM CABINETS WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cabinetID); // set ID parameter
        statement.executeUpdate();

        statement.close();
    }

    public int getMaxId() throws SQLException {
        String query = "SELECT MAX(ID) AS max_id FROM CABINETS";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        int maxId = 0;
        if (resultSet.next()) {
            maxId = resultSet.getInt("max_id");
        }

        statement.close();
        resultSet.close();
        return maxId;
    }

}
