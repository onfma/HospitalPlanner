package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CabinetsScheduleDAO {
    private Connection connection;

    public CabinetsScheduleDAO(Connection connection) {
        this.connection = connection;
    }

    public String  select() throws SQLException {
        String query = "SELECT * FROM CABINETS_SCHEDULE";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cabinetID", resultSet.getInt("CABINET_ID"));
            jsonObject.put("dayOfWeek", resultSet.getString("DAY_OF_WEEK"));
            jsonObject.put("startTime", resultSet.getString("START_TIME"));
            jsonObject.put("endTime", resultSet.getString("END_TIME"));

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }


    public void insert(CabinetSchedule cabinetSchedule) throws SQLException {
        String query = "INSERT INTO CABINETS_SCHEDULE (CABINET_ID, DAY_OF_WEEK, START_TIME, END_TIME) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, cabinetSchedule.getId());
        statement.setString(2, cabinetSchedule.getDayOfWeek());
        statement.setString(3, String.valueOf(cabinetSchedule.getStartTime()));
        statement.setString(4, String.valueOf(cabinetSchedule.getEndTime()));
        statement.executeUpdate();

        statement.close();
    }

    public void delete(CabinetSchedule cabinetSchedule) throws SQLException {
        String query = "DELETE FROM CABINETS_SCHEDULE WHERE CABINET_ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cabinetSchedule.getId()); // set ID parameter
        statement.executeUpdate();

        statement.close();
    }

    public String getCabinetSchedule_SpecificDay(Long cabinetID, String dayOfWeek) throws SQLException {
        String query = "SELECT * FROM CABINETS_SCHEDULE WHERE CABINET_ID = ? AND DAY_OF_WEEK = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cabinetID); // set CABINET_ID parameter
        statement.setString(2, dayOfWeek);  // set DAY_OF_WEEK parameter
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cabinetID", resultSet.getLong("CABINET_ID"));
            jsonObject.put("dayOfWeek", resultSet.getString("DAY_OF_WEEK"));
            jsonObject.put("startTime", resultSet.getObject("START_TIME", LocalTime.class).toString());
            jsonObject.put("endTime", resultSet.getObject("END_TIME", LocalTime.class).toString());

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }

    public String getCabinetSchedule_FullWeek(Long cabinetID) throws SQLException {
        String query = "SELECT * FROM CABINETS_SCHEDULE WHERE CABINET_ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cabinetID); // set CABINET_ID parameter
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cabinetID", resultSet.getLong("CABINET_ID"));
            jsonObject.put("dayOfWeek", resultSet.getString("DAY_OF_WEEK"));
            jsonObject.put("startTime", resultSet.getObject("START_TIME", LocalTime.class).toString());
            jsonObject.put("endTime", resultSet.getObject("END_TIME", LocalTime.class).toString());

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }

    public void setStartTimeSpecificDay(Long cabinetID, String dayOfWeek, LocalTime startTime) throws SQLException {
        String query = "UPDATE CABINETS_SCHEDULE SET START_TIME = ? WHERE CABINET_ID = ? AND DAY_OF_WEEK = ?";


        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, String.valueOf(startTime)); // set START_TIME parameter
        statement.setLong(2, cabinetID); // set CABINET_ID parameter
        statement.setString(3, dayOfWeek); // set DAY_OF_WEEK parameter
        statement.executeUpdate();

        statement.close();
    }

    public void setEndTimeSpecificDay(Long cabinetID, String dayOfWeek, LocalTime endTime) throws SQLException {
        String query = "UPDATE CABINETS_SCHEDULE SET END_TIME = ? WHERE CABINET_ID = ? AND DAY_OF_WEEK = ?";


        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, String.valueOf(endTime)); // set END_TIME parameter
        statement.setLong(2, cabinetID); // set END_TIME parameter
        statement.setString(3, dayOfWeek); // set DAY_OF_WEEK parameter
        statement.executeUpdate();

        statement.close();
    }

    public void deleteSpecificCabinetDaySchedule(Long cabinetID, String dayOfWeek) throws SQLException {
        String query = "DELETE FROM CABINETS_SCHEDULE WHERE CABINET_ID = ? AND DAY_OF_WEEK = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cabinetID); // set CABINET_ID parameter
        statement.setString(2, dayOfWeek); // set DAY_OF_WEEK parameter
        statement.executeUpdate();

        statement.close();
    }

    public void deleteALLCabinetSchedule(Long cabinetID) throws SQLException {
        String query = "DELETE FROM CABINETS_SCHEDULE WHERE CABINET_ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cabinetID); // set cabinetID parameter
        statement.executeUpdate();

        statement.close();
    }
}
