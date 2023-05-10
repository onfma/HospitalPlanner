package com.example.hospitalplanner.database;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {

    private static final String URL ="jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "Arcadia";
    private static final String PASSWORD = "Arcadia";
    private static ComboPooledDataSource cpds;
    private static DBManager instance;

    private DBManager() {
        cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            System.out.println("Error at driver class!");
            System.err.println(e);
        }
        cpds.setJdbcUrl(URL);
        cpds.setUser(USER);
        cpds.setPassword(PASSWORD);
        cpds.setMinPoolSize(3);
        cpds.setAcquireIncrement(50);
        cpds.setMaxPoolSize(10000);
    }

    public static DBManager getInstance() throws SQLException{
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    public void closeConnection() {
        try {
            cpds.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}