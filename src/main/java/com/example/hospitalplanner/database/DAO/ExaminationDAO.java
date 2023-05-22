package com.example.hospitalplanner.database.DAO;

import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.Examination;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ExaminationDAO {
    private Connection connection;

    public ExaminationDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Examination> select() throws SQLException {
        String query = "SELECT * FROM EXAMINATION";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<Examination> examinationList = new ArrayList<>();
        while (resultSet.next()) {
            Examination examination = new Examination();

            examination.setIdExamination(resultSet.getInt("ID_EXAMINATION"));
            examination.setIdCabinet(resultSet.getInt("ID_CABINET"));
            examination.setExaminationName(resultSet.getString("EXAMINATION_NAME"));

            if (resultSet.getFloat("AVERAGE_DURATION") != 0)
                examination.setAverageDuration(resultSet.getFloat("AVERAGE_DURATION"));

            examinationList.add(examination);
        }

        statement.close();
        resultSet.close();
        return examinationList;
    }

    public List<Examination> getCabinetExamination(int idCabinet) throws SQLException {
        String query = "SELECT * FROM EXAMINATION WHERE ID_CABINET = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idCabinet); // set parameter
        ResultSet resultSet = statement.executeQuery();

        List<Examination> examinationList = new ArrayList<>();
        while (resultSet.next()) {
            Examination examination = new Examination();

            examination.setIdExamination(resultSet.getInt("ID_EXAMINATION"));
            examination.setIdCabinet(resultSet.getInt("ID_CABINET"));
            examination.setExaminationName(resultSet.getString("EXAMINATION_NAME"));

            if (resultSet.getFloat("AVERAGE_DURATION") != 0)
                examination.setAverageDuration(resultSet.getFloat("AVERAGE_DURATION"));

            examinationList.add(examination);
        }

        statement.close();
        resultSet.close();
        return examinationList;
    }

    public void setIdCabinet(int idExamination, int idCabinet) throws SQLException {
        String query = "UPDATE EXAMINATION SET ID_CABINET = ? WHERE ID_EXAMINATION = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idCabinet);  // set ID_CABINET parameter
        statement.setInt(2, idExamination);    // set ID_EXAMINATION parameter

        statement.executeUpdate();

        statement.close();
    }

    public void setExaminationName(int idExamination, String examinationName) throws SQLException {
        String query = "UPDATE EXAMINATION SET EXAMINATION_NAME = ? WHERE ID_EXAMINATION = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, examinationName);  // set EXAMINATION_NAME parameter
        statement.setInt(2, idExamination);    // set ID_EXAMINATION parameter

        statement.executeUpdate();

        statement.close();
    }

    public void setAverageDuration(int idExamination, float averageDuration) throws SQLException {
        String query = "UPDATE EXAMINATION SET AVERAGE_DURATION = ? WHERE ID_EXAMINATION = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setFloat(1, averageDuration);  // set AVERAGE_DURATION parameter
        statement.setInt(2, idExamination);    // set ID_EXAMINATION parameter

        statement.executeUpdate();

        statement.close();
    }

    public float getAverageDuration(int idExamination) throws SQLException {
        String query = "SELECT AVERAGE_DURATION FROM EXAMINATION WHERE ID_EXAMINATION = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idExamination); // set ID_EXAMINATION parameter
        ResultSet resultSet = statement.executeQuery();

        float averageDuration = 0;
        while (resultSet.next()) {
            averageDuration = resultSet.getFloat("AVERAGE_DURATION");
        }

        statement.close();
        resultSet.close();
        return averageDuration;
    }

    public void insert(Examination examination) throws SQLException {
        String query = "INSERT INTO EXAMINATION (ID_EXAMINATION, ID_CABINET, EXAMINATION_NAME) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, examination.getIdExamination());
        statement.setInt(2, examination.getIdCabinet());
        statement.setString(3, examination.getExaminationName());
        statement.executeUpdate();

        statement.close();
    }

    public void delete(int idExamination) throws SQLException {
        String query = "DELETE FROM EXAMINATION WHERE ID_EXAMINATION = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idExamination);
        statement.executeUpdate();

        statement.close();
    }
}
