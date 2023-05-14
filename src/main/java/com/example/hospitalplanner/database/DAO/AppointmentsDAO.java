package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsDAO {
    private Connection connection;

    public AppointmentsDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Appoinments> select() throws SQLException {
        String query = "SELECT * FROM APPOINTMENTS";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<Appoinments> appoinmentsList = new ArrayList<>();
        while (resultSet.next()) {
            Appoinments appoinment = new Appoinments();
            appoinment.setId(resultSet.getInt("ID"));
            appoinment.setCabinetID(resultSet.getInt("CABINET_ID"));
            appoinment.setDoctorCNP(resultSet.getLong("DOCTOR_CNP"));
            appoinment.setPatientCNP(resultSet.getLong("PATIENT_CNP"));
            appoinment.setAppointmentTime(LocalDateTime.parse(resultSet.getString("APPOINTMENT_TIME")));

            appoinmentsList.add(appoinment);
        }

        statement.close();
        resultSet.close();
        return appoinmentsList;
    }


    public void insert(Appoinments appoinment) throws SQLException {
        String query = "INSERT INTO APPOINTMENTS (ID, CABINET_ID, DOCTOR_CNP, PATIENT_CNP, APPOINTMENT_TIME) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, appoinment.getId());
        statement.setInt(2, appoinment.getCabinetID());
        statement.setLong(3, appoinment.getDoctorCNP());
        statement.setLong(4, appoinment.getPatientCNP());
        statement.setString(4, String.valueOf(appoinment.getAppointmenTime()));
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
            jsonObject.put("appointmentTime", resultSet.getObject("APPOINTMENT_TIME", LocalTime.class).toString());

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
            jsonObject.put("appointmentTime", resultSet.getObject("APPOINTMENT_TIME", LocalTime.class).toString());

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
            jsonObject.put("appointmentTime", resultSet.getObject("APPOINTMENT_TIME", LocalTime.class).toString());

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
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

    ////////////////

    public String getDoctorSchedule_SpecificDay(Long doctorCNP, String dayOfWeek) throws SQLException {
        String query = "SELECT * FROM DOCTORS_SCHEDULE WHERE DOCTOR_CNP = ? AND DAY_OF_WEEK = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set DOCTOR_CNP parameter
        statement.setString(2, dayOfWeek);
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("doctor_CNP", resultSet.getLong("DOCTOR_CNP"));
            jsonObject.put("dayOfWeek", resultSet.getString("DAY_OF_WEEK"));
            jsonObject.put("startTime", resultSet.getObject("START_TIME", LocalTime.class).toString());
            jsonObject.put("endTime", resultSet.getObject("END_TIME", LocalTime.class).toString());

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }

    public String getDoctorSchedule_FullWeek(Long doctorCNP) throws SQLException {
        String query = "SELECT * FROM DOCTORS_SCHEDULE WHERE DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("doctor_CNP", resultSet.getLong("DOCTOR_CNP"));
            jsonObject.put("dayOfWeek", resultSet.getString("DAY_OF_WEEK"));
            jsonObject.put("startTime", resultSet.getObject("START_TIME", LocalTime.class).toString());
            jsonObject.put("endTime", resultSet.getObject("END_TIME", LocalTime.class).toString());

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
}
