package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.person.Doctor;
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

    public List<DoctorSpeciality> select() throws SQLException {
        String query = "SELECT * FROM DOCTOR_SPECIALTIES";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<DoctorSpeciality> doctorSpecialityList = new ArrayList<>();
        while (resultSet.next()) {
            DoctorSpeciality doctorSpeciality = new DoctorSpeciality();
            doctorSpeciality.setCabinetID(resultSet.getInt("CABINET_ID"));
            doctorSpeciality.setDoctorCNP(resultSet.getLong("DOCTOR_CNP"));

            doctorSpecialityList.add(doctorSpeciality);
        }

        statement.close();
        resultSet.close();
        return doctorSpecialityList;
    }

    public String selectJSON() throws SQLException {
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

    public List<DoctorSpeciality> getDoctorSpecialities(Long doctorCNP) throws SQLException {
        String query = "SELECT * FROM DOCTOR_SPECIALTIES WHERE DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        List<DoctorSpeciality> doctorSpecialityList = new ArrayList<>();
        while (resultSet.next()) {
            DoctorSpeciality doctorSpeciality = new DoctorSpeciality();
            doctorSpeciality.setDoctorCNP(resultSet.getLong("DOCTOR_CNP"));
            doctorSpeciality.setCabinetID(resultSet.getInt("CABINET_ID"));

            doctorSpecialityList.add(doctorSpeciality);
        }

        statement.close();
        resultSet.close();
        return doctorSpecialityList;
    }

    public String getDoctorSpecialitiesJSON(Long doctorCNP) throws SQLException {
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

    public String getDoctorsHaveSameSpecialityJSON(int cabinetID) throws SQLException {
        String query = "SELECT * FROM DOCTOR_SPECIALTIES WHERE CABINET_ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, cabinetID); // set DOCTOR_CNP parameter
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

    public List<Doctor> getDoctorsHaveSameSpeciality(int cabinetID) throws SQLException {
        String query = "SELECT * FROM DOCTOR_SPECIALTIES WHERE CABINET_ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, cabinetID); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        DoctorDAO doctorDAO = new DoctorDAO(connection);

        List<Doctor> doctorList = new ArrayList<>();
        while (resultSet.next()) {
            Doctor doctor = new Doctor();
            doctor = doctorDAO.findByCNP(resultSet.getLong("DOCTOR_CNP"));

            doctorList.add(doctor);
        }

        statement.close();
        resultSet.close();
        return doctorList;
    }


    public void deleteAllDoctorSpecialities(Long doctorCNP) throws SQLException {
        String query = "DELETE FROM DOCTOR_SPECIALTIES WHERE DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set doctorCNP parameter
        statement.executeUpdate();

        statement.close();
    }

    public void deleteAllCabinetsDoctor(int cabinetID) throws SQLException {
        String query = "DELETE FROM DOCTOR_SPECIALTIES WHERE CABINET_ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, cabinetID); // set CABINET_ID parameter
        statement.executeUpdate();

        statement.close();
    }

    public void delete(long doctorCNP, int cabinetID) throws SQLException {
        String query = "DELETE FROM DOCTOR_SPECIALTIES WHERE CABINET_ID = ? AND DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, cabinetID); // set DOCTOR_CNP parameter
        statement.setLong(2, doctorCNP);
        statement.executeUpdate();

        statement.close();
    }
}
