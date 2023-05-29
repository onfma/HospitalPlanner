package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.models.AppointmentModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsModelDAO {
    private Connection connection;

    public AppointmentsModelDAO(Connection connection) {
        this.connection = connection;
    }

    public List<AppointmentModel> select() throws SQLException {
        String query = "SELECT * FROM APPOINTMENTSMODEL";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<AppointmentModel> appoinmentsList = new ArrayList<>();
        while (resultSet.next()) {
            AppointmentModel appoinment = new AppointmentModel();
            appoinment.setId(resultSet.getInt("ID"));
            appoinment.setCabinetId(resultSet.getInt("CABINET_ID"));
            appoinment.setDoctorCNP(resultSet.getLong("DOCTOR_CNP"));
            appoinment.setPatientCNP(resultSet.getLong("DOCTOR_CNP"));
            appoinment.setAppointmentTime(LocalDateTime.parse(resultSet.getString("APPOINTMENT_TIME")));

            if(resultSet.getInt("DURATION") != 0)
                appoinment.setDuration(resultSet.getInt("DURATION"));
            if(resultSet.getString("DIAGNOSIS") != null)
                appoinment.setDiagnosis(resultSet.getString("DIAGNOSIS"));
            if(resultSet.getString("TREATMENT") != null)
                appoinment.setDiagnosis(resultSet.getString("TREATMENT"));

            appoinment.setExaminationID(resultSet.getInt("ID_EXAMINATION"));

            appoinmentsList.add(appoinment);
        }

        statement.close();
        resultSet.close();
        return appoinmentsList;
    }

    public String selectJSON() throws SQLException {
        String query = "SELECT * FROM APPOINTMENTSMODEL";
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


    public void insert(AppointmentModel appoinment) throws SQLException {
        String query = "INSERT INTO APPOINTMENTSMODEL (ID, CABINET_ID, DOCTOR_CNP, PATIENT_CNP, APPOINTMENT_TIME, ID_EXAMINATION) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, appoinment.getId());
        statement.setInt(2, appoinment.getCabinetId());
        statement.setLong(3, appoinment.getDoctorCNP());
        statement.setLong(4, appoinment.getPatientCNP());
        statement.setTimestamp(5, Timestamp.valueOf((appoinment.getAppointmentTime())));
        statement.setInt(6, appoinment.getExaminationID());
        statement.executeUpdate();

        statement.close();
    }

    public String getDoctorAppointmentsJSON(Long doctorCNP) throws SQLException {
        String query = "SELECT * FROM APPOINTMENTSMODEL WHERE DOCTOR_CNP = ?";
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

    public List<AppointmentModel> getDoctorAppointments(Long doctorCNP) throws SQLException {
        String query = "SELECT * FROM APPOINTMENTSMODEL WHERE DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        List<AppointmentModel> appoinmentsList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while (resultSet.next()) {
            AppointmentModel appoinment = new AppointmentModel();
            appoinment.setId(resultSet.getInt("ID"));
            appoinment.setCabinetId(resultSet.getInt("CABINET_ID"));
            appoinment.setDoctorCNP(resultSet.getLong("DOCTOR_CNP"));
            appoinment.setPatientCNP(resultSet.getLong("PATIENT_CNP"));
            appoinment.setAppointmentTime(LocalDateTime.parse(resultSet.getString("APPOINTMENT_TIME"), formatter));

            if(resultSet.getInt("DURATION") != 0)
                appoinment.setDuration(resultSet.getInt("DURATION"));
            if(resultSet.getString("DIAGNOSIS") != null)
                appoinment.setDiagnosis(resultSet.getString("DIAGNOSIS"));
            if(resultSet.getString("TREATMENT") != null)
                appoinment.setDiagnosis(resultSet.getString("TREATMENT"));

            appoinment.setExaminationID(resultSet.getInt("ID_EXAMINATION"));

            appoinmentsList.add(appoinment);
        }

        statement.close();
        resultSet.close();
        return appoinmentsList;
    }

    public String getPatientAppointmentsJSON(Long patientCNP) throws SQLException {
        String query = "SELECT * FROM APPOINTMENTSMODEL WHERE PATIENT_CNP = ?";
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

    public List<AppointmentModel> getPatientAppointments(Long patientCNP) throws SQLException {
        String query = "SELECT * FROM APPOINTMENTSMODEL WHERE PATIENT_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, patientCNP); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        List<AppointmentModel> appoinmentsList = new ArrayList<>();
        while (resultSet.next()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            AppointmentModel appoinment = new AppointmentModel();
            appoinment.setId(resultSet.getInt("ID"));
            appoinment.setCabinetId(resultSet.getInt("CABINET_ID"));
            appoinment.setDoctorCNP(resultSet.getLong("DOCTOR_CNP"));
            appoinment.setPatientCNP(resultSet.getLong("DOCTOR_CNP"));
            appoinment.setAppointmentTime(LocalDateTime.parse(resultSet.getString("APPOINTMENT_TIME"), formatter));

            if(resultSet.getInt("DURATION") != 0)
                appoinment.setDuration(resultSet.getInt("DURATION"));
            if(resultSet.getString("DIAGNOSIS") != null)
                appoinment.setDiagnosis(resultSet.getString("DIAGNOSIS"));
            if(resultSet.getString("TREATMENT") != null)
                appoinment.setDiagnosis(resultSet.getString("TREATMENT"));

            appoinment.setExaminationID(resultSet.getInt("ID_EXAMINATION"));

            appoinmentsList.add(appoinment);
        }

        statement.close();
        resultSet.close();
        return appoinmentsList;
    }

    public List<AppointmentModel> getAppointmentsSameExamination(int idExamination) throws SQLException {
        String query = "SELECT * FROM APPOINTMENTSMODEL WHERE ID_EXAMINATION = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idExamination); // set ID_EXAMINATION parameter
        ResultSet resultSet = statement.executeQuery();

        List<AppointmentModel> appoinmentsList = new ArrayList<>();
        while (resultSet.next()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            AppointmentModel appoinment = new AppointmentModel();
            appoinment.setId(resultSet.getInt("ID"));
            appoinment.setCabinetId(resultSet.getInt("CABINET_ID"));
            appoinment.setDoctorCNP(resultSet.getLong("DOCTOR_CNP"));
            appoinment.setPatientCNP(resultSet.getLong("DOCTOR_CNP"));
            appoinment.setAppointmentTime(LocalDateTime.parse(resultSet.getString("APPOINTMENT_TIME"), formatter));

            if(resultSet.getInt("DURATION") != 0)
                appoinment.setDuration(resultSet.getInt("DURATION"));
            if(resultSet.getString("DIAGNOSIS") != null)
                appoinment.setDiagnosis(resultSet.getString("DIAGNOSIS"));
            if(resultSet.getString("TREATMENT") != null)
                appoinment.setDiagnosis(resultSet.getString("TREATMENT"));

            appoinment.setExaminationID(resultSet.getInt("ID_EXAMINATION"));

            appoinmentsList.add(appoinment);
        }

        statement.close();
        resultSet.close();
        return appoinmentsList;
    }

    public String getCabinetAppointmentsJSON(int cabinetID) throws SQLException {
        String query = "SELECT * FROM APPOINTMENTSMODEL WHERE CABINET_ID = ?";
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

    public List<AppointmentModel> getCabinetAppointments(int cabinetID) throws SQLException {
        String query = "SELECT * FROM APPOINTMENTSMODEL WHERE CABINET_ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cabinetID); // set DOCTOR_CNP parameter
        ResultSet resultSet = statement.executeQuery();

        List<AppointmentModel> appoinmentsList = new ArrayList<>();
        while (resultSet.next()) {
            AppointmentModel appoinment = new AppointmentModel();
            appoinment.setId(resultSet.getInt("ID"));
            appoinment.setCabinetId(resultSet.getInt("CABINET_ID"));
            appoinment.setDoctorCNP(resultSet.getLong("DOCTOR_CNP"));
            appoinment.setPatientCNP(resultSet.getLong("DOCTOR_CNP"));
            appoinment.setAppointmentTime(LocalDateTime.parse(resultSet.getString("APPOINTMENT_TIME")));

            if(resultSet.getInt("DURATION") != 0)
                appoinment.setDuration(resultSet.getInt("DURATION"));
            if(resultSet.getString("DIAGNOSIS") != null)
                appoinment.setDiagnosis(resultSet.getString("DIAGNOSIS"));
            if(resultSet.getString("TREATMENT") != null)
                appoinment.setDiagnosis(resultSet.getString("TREATMENT"));

            appoinment.setExaminationID(resultSet.getInt("ID_EXAMINATION"));

            appoinmentsList.add(appoinment);
        }

        statement.close();
        resultSet.close();
        return appoinmentsList;
    }

    public AppointmentModel findAppoinment(int id) throws SQLException {
        String query = "SELECT * FROM APPOINTMENTSMODEL WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id); // set ID parameter
        ResultSet resultSet = statement.executeQuery();

        AppointmentModel appoinment = new AppointmentModel();
        while (resultSet.next()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            appoinment.setId(resultSet.getInt("ID"));
            appoinment.setCabinetId(resultSet.getInt("CABINET_ID"));
            appoinment.setDoctorCNP(resultSet.getLong("DOCTOR_CNP"));
            appoinment.setPatientCNP(resultSet.getLong("PATIENT_CNP"));
            appoinment.setAppointmentTime(LocalDateTime.parse(resultSet.getString("APPOINTMENT_TIME"), formatter));
            appoinment.setExaminationID(resultSet.getInt("ID_EXAMINATION"));
        }

        statement.close();
        resultSet.close();
        return appoinment;
    }

    public void setAppointmentTime(int id, LocalDateTime newAppointmentTime) throws SQLException {
        String query = "UPDATE APPOINTMENTSMODEL SET APPOINTMENT_TIME = ? WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setTimestamp(1, Timestamp.valueOf((newAppointmentTime)));  // set APPOINTMENT_TIME parameter
        statement.setInt(2, id);    // set ID parameter

        statement.executeUpdate();

        statement.close();
    }

    public void setDuration(int id, int duration) throws SQLException {
        String query = "UPDATE APPOINTMENTSMODEL SET DURATION = ? WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, duration);  // set DURATION parameter
        statement.setInt(2, id);    // set ID parameter

        statement.executeUpdate();

        statement.close();
    }

    public void setDiagnosis(int id, String diagnosis) throws SQLException {
        String query = "UPDATE APPOINTMENTSMODEL SET DIAGNOSIS = ? WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, diagnosis);  // set DIAGNOSIS parameter
        statement.setInt(2, id);    // set ID parameter

        statement.executeUpdate();

        statement.close();
    }

    public void setTreatment(int id, String treatment) throws SQLException {
        String query = "UPDATE APPOINTMENTSMODEL SET TREATMENT = ? WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, treatment);  // set TREATMENT parameter
        statement.setInt(2, id);    // set ID parameter

        statement.executeUpdate();

        statement.close();
    }

    public void setDoctor(int id, Long doctorCNP) throws SQLException {
        String query = "UPDATE APPOINTMENTSMODEL SET DOCTOR_CNP = ? WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP);  // set DOCTOR_CNP parameter
        statement.setInt(2, id);    // set ID parameter

        statement.executeUpdate();

        statement.close();
    }

    public void setPatient(int id, Long patientCNP) throws SQLException {
        String query = "UPDATE APPOINTMENTSMODEL SET PATIENT_CNP = ? WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, patientCNP);  // set PATIENT_CNP parameter
        statement.setInt(2, id);    // set ID parameter

        statement.executeUpdate();

        statement.close();
    }

    public int getCabinetID(int id) throws SQLException {
        String query = "SELECT CABINET_ID FROM APPOINTMENTSMODEL WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id); // set ID parameter
        ResultSet resultSet = statement.executeQuery();

        int cabinetID = 0;
        while (resultSet.next())
            cabinetID = (resultSet.getInt("CABINET_ID"));

        statement.close();
        resultSet.close();
        return cabinetID;
    }



    public void delete(int id) throws SQLException {
        String query = "DELETE FROM APPOINTMENTSMODEL WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.executeUpdate();

        statement.close();
    }

    public void deleteAll() throws SQLException {
        String query = "DELETE FROM APPOINTMENTSMODEL";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        statement.close();
    }

    public void deleteAllPatientAppointments(long patientCNP) throws SQLException {
        String query = "DELETE FROM APPOINTMENTSMODEL WHERE PATIENT_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, patientCNP);
        statement.executeUpdate();

        statement.close();
    }

    public void deleteAllDoctorAppointments(long doctorCNP) throws SQLException {
        String query = "DELETE FROM APPOINTMENTSMODEL WHERE DOCTOR_CNP = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, doctorCNP);
        statement.executeUpdate();

        statement.close();
    }

    public void deleteAllCabinetAppointments(int cabinetID) throws SQLException {
        String query = "DELETE FROM APPOINTMENTSMODEL WHERE CABINET_ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cabinetID);
        statement.executeUpdate();

        statement.close();
    }

    public int getMaxAppointmentId() throws SQLException {
        String query = "SELECT MAX(ID) FROM APPOINTMENTSMODEL";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        int maxId = 0;
        if (resultSet.next()) {
            maxId = resultSet.getInt(1);
        }

        statement.close();
        resultSet.close();
        return maxId;
    }

}
