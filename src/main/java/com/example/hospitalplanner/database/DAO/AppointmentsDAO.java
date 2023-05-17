package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsDAO {
    private Connection connection;

    public AppointmentsDAO(Connection connection) {
        this.connection = connection;
    }

    public String select() throws SQLException {
        String query = "SELECT * FROM APPOINTMENTS";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", resultSet.getInt("ID"));
            jsonObject.put("cabinetID", resultSet.getInt("CABINET_ID"));
            jsonObject.put("doctorCNP", resultSet.getLong("DOCTOR_CNP"));
            jsonObject.put("patientCNP", resultSet.getLong("PATIENT_CNP"));
            jsonObject.put("appointmentTime", resultSet.getString("APPOINTMENT_TIME"));

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }


    public void insert(Appoinments appoinment) throws SQLException {
        String query = "INSERT INTO APPOINTMENTS (ID, CABINET_ID, DOCTOR_CNP, PATIENT_CNP, APPOINTMENT_TIME) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, appoinment.getId());
        statement.setInt(2, appoinment.getCabinetID());
        statement.setLong(3, appoinment.getDoctorCNP());
        statement.setLong(4, appoinment.getPatientCNP());
        statement.setTimestamp(5, Timestamp.valueOf((appoinment.getAppointmenTime())));
        statement.executeUpdate();

        statement.close();
    }

    public String getDoctorAppointments(Long doctorCNP) throws SQLException {
        String query = "SELECT * FROM APPOINTMENTS WHERE DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", resultSet.getInt("ID"));
            jsonObject.put("cabinetID", resultSet.getInt("CABINET_ID"));
            jsonObject.put("doctor_CNP", resultSet.getLong("DOCTOR_CNP"));
            jsonObject.put("patient_CNP", resultSet.getLong("PATIENT_CNP"));
            jsonObject.put("appointmentTime", resultSet.getObject("APPOINTMENT_TIME", LocalDateTime.class).toString());

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }

    public String getPatientAppointments(Long patientCNP) throws SQLException {
        String query = "SELECT * FROM APPOINTMENTS WHERE PATIENT_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, patientCNP); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", resultSet.getInt("ID"));
            jsonObject.put("cabinetID", resultSet.getInt("CABINET_ID"));
            jsonObject.put("doctor_CNP", resultSet.getLong("DOCTOR_CNP"));
            jsonObject.put("patient_CNP", resultSet.getLong("PATIENT_CNP"));
            jsonObject.put("appointmentTime", resultSet.getObject("APPOINTMENT_TIME", LocalDateTime.class).toString());

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }

    public String getCabinetAppointments(int cabinetID) throws SQLException {
        String query = "SELECT * FROM APPOINTMENTS WHERE CABINET_ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cabinetID); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", resultSet.getInt("ID"));
            jsonObject.put("cabinetID", resultSet.getInt("CABINET_ID"));
            jsonObject.put("doctor_CNP", resultSet.getLong("DOCTOR_CNP"));
            jsonObject.put("patient_CNP", resultSet.getLong("PATIENT_CNP"));
            jsonObject.put("appointmentTime", resultSet.getObject("APPOINTMENT_TIME", LocalDateTime.class).toString());

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }

    public void setAppointmentTime(int id, LocalDateTime newAppointmentTime) throws SQLException {
        String query = "UPDATE APPOINTMENTS SET APPOINTMENT_TIME = ? WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setTimestamp(1, Timestamp.valueOf((newAppointmentTime)));  // set APPOINTMENT_TIME parameter
        statement.setInt(2, id);    // set ID parameter

        statement.executeUpdate();

        statement.close();
    }

    public void setDoctor(int id, Long doctorCNP) throws SQLException {
        String query = "UPDATE APPOINTMENTS SET DOCTOR_CNP = ? WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP);  // set DOCTOR_CNP parameter
        statement.setInt(2, id);    // set ID parameter

        statement.executeUpdate();

        statement.close();
    }

    public void setPatient(int id, Long patientCNP) throws SQLException {
        String query = "UPDATE APPOINTMENTS SET PATIENT_CNP = ? WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, patientCNP);  // set PATIENT_CNP parameter
        statement.setInt(2, id);    // set ID parameter

        statement.executeUpdate();

        statement.close();
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM APPOINTMENTS WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.executeUpdate();

        statement.close();
    }

    public void deleteAllPatientAppointments(long patientCNP) throws SQLException {
        String query = "DELETE FROM APPOINTMENTS WHERE PATIENT_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, patientCNP);
        statement.executeUpdate();

        statement.close();
    }

    public void deleteAllDoctorAppointments(long doctorCNP) throws SQLException {
        String query = "DELETE FROM APPOINTMENTS WHERE DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP);
        statement.executeUpdate();

        statement.close();
    }

    public void deleteAllCabinetAppointments(int cabinetID) throws SQLException {
        String query = "DELETE FROM APPOINTMENTS WHERE CABINET_ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cabinetID);
        statement.executeUpdate();

        statement.close();
    }
}
