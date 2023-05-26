package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.person.Person;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorsScheduleDAO {
    private Connection connection;

    public DoctorsScheduleDAO(Connection connection) {
        this.connection = connection;
    }

    public List<DoctorSchedule> select() throws SQLException {
        String query = "SELECT * FROM DOCTORS_SCHEDULE";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<DoctorSchedule> doctorScheduleList = new ArrayList<>();
        while (resultSet.next()) {
            DoctorSchedule doctorSchedule = new DoctorSchedule();
            doctorSchedule.setId(resultSet.getLong("DOCTOR_CNP"));
            doctorSchedule.setDayOfWeek(resultSet.getString("DAY_OF_WEEK"));
            doctorSchedule.setStartTime(LocalTime.parse(resultSet.getString("START_TIME")));
            doctorSchedule.setEndTime(LocalTime.parse(resultSet.getString("END_TIME")));

            doctorScheduleList.add(doctorSchedule);
        }

        statement.close();
        resultSet.close();
        return doctorScheduleList;
    }

    public String selectJSON() throws SQLException {
        String query = "SELECT * FROM DOCTORS_SCHEDULE";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("doctorCNP", resultSet.getLong("DOCTOR_CNP"));
            jsonObject.put("dayOfWeek", resultSet.getString("DAY_OF_WEEK"));
            jsonObject.put("startTime", resultSet.getString("START_TIME"));
            jsonObject.put("endTime", resultSet.getString("END_TIME"));

            jsonArray.put(jsonObject);
        }

        statement.close();
        resultSet.close();
        return jsonArray.toString();
    }


    public void insert(DoctorSchedule doctorSchedule) throws SQLException {
        String query = "INSERT INTO DOCTORS_SCHEDULE (DOCTOR_CNP, DAY_OF_WEEK, START_TIME, END_TIME) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, doctorSchedule.getId());
        statement.setString(2, doctorSchedule.getDayOfWeek());
        statement.setString(3, String.valueOf(doctorSchedule.getStartTime()));
        statement.setString(4, String.valueOf(doctorSchedule.getEndTime()));
        statement.executeUpdate();

        statement.close();
    }

    public String getDoctorSchedule_SpecificDayJSON(Long doctorCNP, String dayOfWeek) throws SQLException {
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


    public DoctorSchedule getDoctorSchedule_SpecificDay(Long doctorCNP, String dayOfWeek) throws SQLException {
        String query = "SELECT * FROM DOCTORS_SCHEDULE WHERE DOCTOR_CNP = ? AND DAY_OF_WEEK = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set DOCTOR_CNP parameter
        statement.setString(2, dayOfWeek);
        ResultSet resultSet = statement.executeQuery();

        DoctorSchedule doctorSchedule = new DoctorSchedule();
        while (resultSet.next()) {
            doctorSchedule.setId(resultSet.getLong("DOCTOR_CNP"));
            doctorSchedule.setDayOfWeek(resultSet.getString("DAY_OF_WEEK"));
            doctorSchedule.setStartTime(resultSet.getObject("START_TIME", LocalTime.class));
            doctorSchedule.setEndTime(resultSet.getObject("END_TIME", LocalTime.class));
        }

        statement.close();
        resultSet.close();
        return doctorSchedule;
    }

    public String getDoctorSchedule_FullWeekJSON(Long doctorCNP) throws SQLException {
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

    public List<DoctorSchedule> getDoctorSchedule_FullWeek(Long doctorCNP) throws SQLException {
        String query = "SELECT * FROM DOCTORS_SCHEDULE WHERE DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        List<DoctorSchedule> doctorScheduleList = new ArrayList<>();
        while (resultSet.next()) {
            DoctorSchedule doctorSchedule = new DoctorSchedule();

            doctorSchedule.setId(resultSet.getLong("DOCTOR_CNP"));
            doctorSchedule.setDayOfWeek(resultSet.getString("DAY_OF_WEEK"));
            doctorSchedule.setStartTime(resultSet.getObject("START_TIME", LocalTime.class));
            doctorSchedule.setEndTime(resultSet.getObject("END_TIME", LocalTime.class));

            doctorScheduleList.add(doctorSchedule);
        }

        statement.close();
        resultSet.close();
        return doctorScheduleList;
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
