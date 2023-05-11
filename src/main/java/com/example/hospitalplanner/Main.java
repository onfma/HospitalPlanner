package com.example.hospitalplanner;

import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.test.AllTests;
import com.example.hospitalplanner.test.TestPerson;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AllTests allTests = new AllTests();
    }
}
