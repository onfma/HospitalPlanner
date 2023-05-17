package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import com.example.hospitalplanner.entities.schedule.DoctorSpeciality;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorsSpecialitiesDAO {
    private Connection connection;

    public DoctorsSpecialitiesDAO(Connection connection) {
        this.connection = connection;
    }

    public String select() throws SQLException {
        String query = "SELECT * FROM DOCTOR_SPECIALTIES";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cabinetID", resultSet.getInt("CABINET_ID"));
            jsonObject.put("doctorCNP", resultSet.getLong("DOCTOR_CNP"));

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }


    public void insert(DoctorSpeciality doctorSpeciality) throws SQLException {
        String query = "INSERT INTO DOCTOR_SPECIALTIES (CABINET_ID, DOCTOR_CNP) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, doctorSpeciality.getCabinetID());
        statement.setLong(2, doctorSpeciality.getDoctorCNP());
        statement.executeUpdate();

        statement.close();
    }

    public String getDoctorSpecialities(Long doctorCNP) throws SQLException {
        String query = "SELECT * FROM DOCTOR_SPECIALTIES WHERE DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("doctorCNP", resultSet.getLong("DOCTOR_CNP"));
            jsonObject.put("cabinetID", resultSet.getInt("CABINET_ID"));

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }

    public String getCabinetSpecialities(Long doctorCNP) throws SQLException {
        String query = "SELECT * FROM DOCTOR_SPECIALTIES WHERE DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("doctorCNP", resultSet.getLong("DOCTOR_CNP"));
            jsonObject.put("cabinetID", resultSet.getInt("CABINET_ID"));

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }

    public void setStartTimeSpecificDay(Long doctorCNP, String dayOfWeek, LocalTime startTime) throws SQLException {
        String query = "UPDATE DOCTORS_SCHEDULE SET START_TIME = ? WHERE DOCTOR_CNP = ? AND DAY_OF_WEEK = ?";


        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, String.valueOf(startTime)); // set startTime parameter
        statement.setLong(2, doctorCNP); // set doctorCNP parameter
        statement.setString(3, dayOfWeek); // set dayOfWeek parameter
        statement.executeUpdate();

        statement.close();
    }

    public void setEndTimeSpecificDay(Long doctorCNP, String dayOfWeek, LocalTime endTime) throws SQLException {
        String query = "UPDATE DOCTORS_SCHEDULE SET END_TIME = ? WHERE DOCTOR_CNP = ? AND DAY_OF_WEEK = ?";


        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, String.valueOf(endTime)); // set endTime parameter
        statement.setLong(2, doctorCNP); // set doctorCNP parameter
        statement.setString(3, dayOfWeek); // set dayOfWeek parameter
        statement.executeUpdate();

        statement.close();
    }

    public void deleteSpecificDoctorDaySchedule(Long doctorCNP, String dayOfWeek) throws SQLException {
        String query = "DELETE FROM DOCTORS_SCHEDULE WHERE DOCTOR_CNP = ? AND DAY_OF_WEEK = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set doctorCNP parameter
        statement.setString(2, dayOfWeek); // set dayOfWeek parameter
        statement.executeUpdate();

        statement.close();
    }

    public void deleteALLDoctorDaySchedule(Long doctorCNP) throws SQLException {
        String query = "DELETE FROM DOCTORS_SCHEDULE WHERE DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set doctorCNP parameter
        statement.executeUpdate();

        statement.close();
    }

    public void delete(long CNP) throws SQLException {
        String query = "DELETE FROM DOCTORS WHERE CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, CNP);
        statement.executeUpdate();

        statement.close();
    }
}
