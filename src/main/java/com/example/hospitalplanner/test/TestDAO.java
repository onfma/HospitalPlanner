package com.example.hospitalplanner.test;

import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.person.Admin;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.person.Person;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {
    public TestDAO(){
        Connection connection = null;

        try {
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass("oracle.jdbc.driver.OracleDriver");
            cpds.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
            cpds.setUser("Arcadia");
            cpds.setPassword("Arcadia");

            connection = cpds.getConnection();

            testPopulateDB(connection);

//            testAdminDAO(connection);

//            testUserAuthenticationDAO(connection);

//            testPatientDAO(connection);

//            testDoctorDAO(connection);

//            testDoctorScheduleDAO(connection);

//            testCabinetsDAO(connection);

//            testCabinetsScheduleDAO(connection);

//            testAppointmentDAO(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public void testPopulateDB(Connection connection) throws SQLException {

        // insert EMAIL & PASSWORD
        UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(connection);

        Admin admin1 = new Admin("admin1@yahoo.com", "Admin1234");
        Admin admin2 = new Admin("admin2@yahoo.com", "Admin1234");
        Doctor doctor1 = new Doctor("doctor1@yahoo.com", "Doctor1234");
        Doctor doctor2 = new Doctor("doctor2@yahoo.com", "Doctor1234");
        Doctor doctor3 = new Doctor("doctor3@yahoo.com", "Doctor1234");
        Doctor doctor4 = new Doctor("doctor4@yahoo.com", "Doctor1234");
        Patient patient1 = new Patient("patient1@yahoo.com", "Patient1234");
        Patient patient2 = new Patient("patient2@yahoo.com", "Patient1234");
        Patient patient3 = new Patient("patient3@yahoo.com", "Patient1234");
        Patient patient4 = new Patient("patient4@yahoo.com", "Patient1234");

        userAuthenticationDAO.insert(admin1);
        userAuthenticationDAO.insert(admin2);
        userAuthenticationDAO.insert(doctor1);
        userAuthenticationDAO.insert(doctor2);
        userAuthenticationDAO.insert(doctor3);
        userAuthenticationDAO.insert(doctor4);
        userAuthenticationDAO.insert(patient1);
        userAuthenticationDAO.insert(patient2);
        userAuthenticationDAO.insert(patient3);
        userAuthenticationDAO.insert(patient4);

        // insert all the informations about patients
        PatientDAO patientDAO = new PatientDAO(connection);

        Patient Patient1 = new Patient( 5030524268901L, "Patient1", "patient1", 'M', "+441234567891", "patient1@yahoo.com","101B, Palace Street");
        Patient Patient2 = new Patient( 5030524268902L, "Patient2", "patient2", 'M', "+441234567892", "patient2@yahoo.com","102B, Palace Street");
        Patient Patient3 = new Patient( 5030524268903L, "Patient3", "patient3", 'M', "+441234567893", "patient3@yahoo.com","103B, Palace Street");
        Patient Patient4 = new Patient( 5030524268904L, "Patient4", "patient4", 'M', "+441234567894", "patient4@yahoo.com","104B, Palace Street");

        patientDAO.insert(Patient1);
        patientDAO.insert(Patient2);
        patientDAO.insert(Patient3);
        patientDAO.insert(Patient4);

        // insert all the informations about doctors
        DoctorDAO doctorDAO = new DoctorDAO(connection);

        Doctor Doctor1 = new Doctor( 5030524268701L, "Doctor1", "doctor1", 'M', "+441234567891", "doctor1@yahoo.com","101B, Oxford Street");
        Doctor Doctor2 = new Doctor( 5030524268702L, "Doctor2", "doctor2", 'F', "+441234567892", "doctor2@yahoo.com","102B, Oxford Street");
        Doctor Doctor3 = new Doctor( 5030524268703L, "Doctor3", "doctor3", 'M', "+441234567893", "doctor3@yahoo.com","103B, Oxford Street");
        Doctor Doctor4 = new Doctor( 5030524268704L, "Doctor4", "doctor4", 'M', "+441234567894", "doctor4@yahoo.com","104B, Oxford Street");

        doctorDAO.insert(Doctor1);
        doctorDAO.insert(Doctor2);
        doctorDAO.insert(Doctor3);
        doctorDAO.insert(Doctor4);

        // insert Doctor Specialities
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(connection);

        Doctors

        // insert Cabinets
        CabinetsDAO cabinetsDAO = new CabinetsDAO(connection);

        Cabinet cabinet1 = new Cabinet(1, "Cardiology");
        Cabinet cabinet2 = new Cabinet(2, "Neurology");
        Cabinet cabinet3 = new Cabinet(3, "Dermatology");

        cabinetsDAO.insert(cabinet1);
        cabinetsDAO.insert(cabinet2);
        cabinetsDAO.insert(cabinet3);

        // insert CabinetSchedulle
        CabinetsScheduleDAO cabinetsScheduleDAO = new CabinetsScheduleDAO(connection);

        CabinetSchedule mondayC1 = new CabinetSchedule(1, "Monday", LocalTime.of(9,0), LocalTime.of(16,0));
        CabinetSchedule tuesdayC1 = new CabinetSchedule(1, "Tuesday", LocalTime.of(9,30), LocalTime.of(16,30));
        CabinetSchedule wednesdayC1 = new CabinetSchedule(1, "Wednesday", LocalTime.of(10,0), LocalTime.of(17,0));
        CabinetSchedule thursdayC1 = new CabinetSchedule(1, "Thursday", LocalTime.of(10,30), LocalTime.of(17,30));
        CabinetSchedule fridayC1 = new CabinetSchedule(1, "Friday", LocalTime.of(10,15), LocalTime.of(17,15));

        cabinetsScheduleDAO.insert(mondayC1);
        cabinetsScheduleDAO.insert(tuesdayC1);
        cabinetsScheduleDAO.insert(wednesdayC1);
        cabinetsScheduleDAO.insert(thursdayC1);
        cabinetsScheduleDAO.insert(fridayC1);

        CabinetSchedule mondayC2 = new CabinetSchedule(2, "Monday", LocalTime.of(11,0), LocalTime.of(17,0));
        CabinetSchedule tuesdayC2 = new CabinetSchedule(2, "Tuesday", LocalTime.of(10,30), LocalTime.of(19,30));
        CabinetSchedule wednesdayC2 = new CabinetSchedule(2, "Wednesday", LocalTime.of(12,0), LocalTime.of(18,0));
        CabinetSchedule thursdayC2 = new CabinetSchedule(2, "Thursday", LocalTime.of(10,40), LocalTime.of(15,30));
        CabinetSchedule fridayC2 = new CabinetSchedule(2, "Friday", LocalTime.of(9,20), LocalTime.of(18,15));

        cabinetsScheduleDAO.insert(mondayC2);
        cabinetsScheduleDAO.insert(tuesdayC2);
        cabinetsScheduleDAO.insert(wednesdayC2);
        cabinetsScheduleDAO.insert(thursdayC2);
        cabinetsScheduleDAO.insert(fridayC2);

        CabinetSchedule mondayC3 = new CabinetSchedule(3, "Monday", LocalTime.of(9,40), LocalTime.of(16,15));
        CabinetSchedule tuesdayC3 = new CabinetSchedule(3, "Tuesday", LocalTime.of(11,15), LocalTime.of(18,50));
        CabinetSchedule wednesdayC3 = new CabinetSchedule(3, "Wednesday", LocalTime.of(11,30), LocalTime.of(19,45));
        CabinetSchedule thursdayC3 = new CabinetSchedule(3, "Thursday", LocalTime.of(9,45), LocalTime.of(17,50));
        CabinetSchedule fridayC3 = new CabinetSchedule(3, "Friday", LocalTime.of(9,40), LocalTime.of(18,45));

        cabinetsScheduleDAO.insert(mondayC3);
        cabinetsScheduleDAO.insert(tuesdayC3);
        cabinetsScheduleDAO.insert(wednesdayC3);
        cabinetsScheduleDAO.insert(thursdayC3);
        cabinetsScheduleDAO.insert(fridayC3);


    }

    public void testAdminDAO(Connection connection) throws SQLException {
        Admin admin = new Admin();
        admin.setEmail("admin@admin.com");

        AdminDAO adminDAO = new AdminDAO(connection);

        adminDAO.insert(admin);
    }

    public void testAppointmentDAO(Connection connection) throws SQLException {
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(connection);

//        Appoinments app1 = new Appoinments(1, 1, 5030524268902L, 5030524268901L, LocalDateTime.of(2023, 6, 01, 10, 0));
//        Appoinments app2 = new Appoinments(2, 1, 5030524268902L, 5031121835501L, LocalDateTime.of(2023, 6, 01, 11, 0));
//        Appoinments app3 = new Appoinments(3, 1, 5030524268902L, 5031124264501L, LocalDateTime.of(2023, 6, 01, 12, 0));

//        appointmentsDAO.insert(app1);
//        appointmentsDAO.insert(app2);
//        appointmentsDAO.insert(app3);

        // Select all the patients
        String appoinmentsJSONobject;

        appoinmentsJSONobject = appointmentsDAO.select();

        System.out.println("\nAll appointments (AFTER INSERTION):\n"+appoinmentsJSONobject);

//        System.out.println(appointmentsDAO.getDoctorAppointments(5030524268902L));

//        appointmentsDAO.setAppointmentTime(app3.getId(), LocalDateTime.of(2023, 6, 01, 15, 0));

//        System.out.println(appointmentsDAO.getPatientAppointments(5031124264501L));

//        System.out.println(appointmentsDAO.getCabinetAppointments(1));

//        appointmentsDAO.delete(app1.getId());
//        appointmentsDAO.delete(app2.getId());
//        appointmentsDAO.delete(app3.getId());


    }

    public void testCabinetsScheduleDAO(Connection connection) throws SQLException{
        CabinetsScheduleDAO cabinetsScheduleDAO = new CabinetsScheduleDAO(connection);

        CabinetSchedule monday = new CabinetSchedule(1, "Monday", LocalTime.of(9,0), LocalTime.of(16,0));
        CabinetSchedule tuesday = new CabinetSchedule(1, "Tuesday", LocalTime.of(9,30), LocalTime.of(16,30));
        CabinetSchedule wednesday = new CabinetSchedule(1, "Wednesday", LocalTime.of(10,0), LocalTime.of(17,0));
        CabinetSchedule thursday = new CabinetSchedule(1, "Thursday", LocalTime.of(10,30), LocalTime.of(17,30));
        CabinetSchedule friday = new CabinetSchedule(1, "Friday", LocalTime.of(10,15), LocalTime.of(17,15));

//        cabinetsScheduleDAO.insert(monday);
//        cabinetsScheduleDAO.insert(tuesday);
//        cabinetsScheduleDAO.insert(wednesday);
//        cabinetsScheduleDAO.insert(thursday);
//        cabinetsScheduleDAO.insert(friday);

        // Select all the cabinetSchedule
        String cabinetScheduleList;

        cabinetScheduleList = cabinetsScheduleDAO.select();

        System.out.println("\nAll cabinets (AFTER INSERTION):\n" + cabinetScheduleList);

        // Delete a specific day from a specific Cabinet Schedule
//        cabinetsScheduleDAO.deleteSpecificCabinetDaySchedule(1L, "Monday");

//        cabinetScheduleList = cabinetsScheduleDAO.select();

//        System.out.println("\nAll cabinets (AFTER DELETING MONDAY PROGRAM):\n" + cabinetScheduleList);

        // Weekly Schedule Program for a Cabinet
//        System.out.println(cabinetsScheduleDAO.getCabinetSchedule_FullWeek(1L));

        // Change startTime for a specific day from a Cabinet's Schedule
//        cabinetsScheduleDAO.setStartTimeSpecificDay(1L, "Tuesday", LocalTime.of(9,15));

        // Change endTime for a specific day from a Cabinet's Schedule
//        cabinetsScheduleDAO.setEndTimeSpecificDay(1L, "Tuesday", LocalTime.of(16,15));

//        cabinetScheduleList = cabinetsScheduleDAO.select();

//        System.out.println("\nAll cabinets (AFTER MODIFY START_TIME / END_TIME):\n" + cabinetScheduleList);

        // Get daily schedule program for Cabinet
//        System.out.println(cabinetsScheduleDAO.getCabinetSchedule_SpecificDay(1L, "Monday"));

        // Delete all schedule program for a specific Cabinet
//        cabinetsScheduleDAO.deleteALLCabinetSchedule(1L);

        // Weekly Schedule Program for Cabinet
//        System.out.println(cabinetsScheduleDAO.getCabinetSchedule_FullWeek(1L));

//        cabinetsScheduleDAO.deleteALLCabinetSchedule(1L);
    }

    public void testCabinetsDAO(Connection connection) throws SQLException {
        CabinetsDAO cabinetsDAO = new CabinetsDAO(connection);

        Cabinet cabinet1 = new Cabinet(1,"Cardiology");
        Cabinet cabinet2 = new Cabinet(2, "Neurology");
        Cabinet cabinet3 = new Cabinet(3, "Dermatology");

//        cabinetsDAO.insert(cabinet1);
//        cabinetsDAO.insert(cabinet2);
//        cabinetsDAO.insert(cabinet3);

        // Select all the patients
        String cabinetList;

        cabinetList = cabinetsDAO.select();

        System.out.println("\nAll cabinets (AFTER INSERTION):\n" + cabinetList);


//        cabinetsDAO.delete(cabinet1);
//        cabinetsDAO.delete(cabinet2);
//        cabinetsDAO.delete(cabinet3);

        // Select all the patients
//        cabinetList = cabinetsDAO.select();

//        System.out.println("\nAll cabinets (AFTER DELETION):\n" + cabinetList);


    }

    public void testDoctorScheduleDAO(Connection connection) throws SQLException {
        DoctorsScheduleDAO doctorsScheduleDAO = new DoctorsScheduleDAO(connection);

        DoctorSchedule monday = new DoctorSchedule(5030524268902L, "Monday", LocalTime.of(9,0), LocalTime.of(16,0));
        DoctorSchedule tuesday = new DoctorSchedule(5030524268902L, "Tuesday", LocalTime.of(9,30), LocalTime.of(16,30));
        DoctorSchedule wednesday = new DoctorSchedule(5030524268902L, "Wednesday", LocalTime.of(10,0), LocalTime.of(17,0));
        DoctorSchedule thursday = new DoctorSchedule(5030524268902L, "Thursday", LocalTime.of(10,30), LocalTime.of(17,30));
        DoctorSchedule friday = new DoctorSchedule(5030524268902L, "Friday", LocalTime.of(10,15), LocalTime.of(17,15));

        doctorsScheduleDAO.insert(monday);
        doctorsScheduleDAO.insert(tuesday);
        doctorsScheduleDAO.insert(wednesday);
        doctorsScheduleDAO.insert(thursday);
        doctorsScheduleDAO.insert(friday);

        // Delete a specific day from a specific Doctor Schedule
        doctorsScheduleDAO.deleteSpecificDoctorDaySchedule(5030524268902L, "Monday");

        // Weekly Schedule Program for Doctor
        System.out.println(doctorsScheduleDAO.getDoctorSchedule_FullWeek(5030524268902L));

        // Change startTime for a specific day from a Doctor's Schedule
        doctorsScheduleDAO.setStartTimeSpecificDay(5030524268902L, "Monday", LocalTime.of(9,15));

        // Change endTime for a specific day from a Doctor's Schedule
        doctorsScheduleDAO.setEndTimeSpecificDay(5030524268902L, "Monday", LocalTime.of(16,15));

        // Get daily schedule program for Doctor
        System.out.println(doctorsScheduleDAO.getDoctorSchedule_SpecificDay(5030524268902L, "Monday"));

        // Delete all schedule program for a specific doctor
        doctorsScheduleDAO.deleteALLDoctorDaySchedule(5030524268902L);

        // Weekly Schedule Program for Doctor
        System.out.println(doctorsScheduleDAO.getDoctorSchedule_FullWeek(5030524268902L));

        doctorsScheduleDAO.deleteALLDoctorDaySchedule(5030524268902L);
    }

    private void testDoctorDAO(Connection connection) throws SQLException {
        UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(connection);

        // Insert a new Patient
        Patient patient1 = new Patient("mihai.alexandru@yahoo.com", "MihaiAlexandru1");

        userAuthenticationDAO.insert(patient1);

        DoctorDAO doctorDAO = new DoctorDAO(connection);

        // Select all the patients
        List<Doctor> doctorList = new ArrayList<>();

        // Insert a new Patient
        Doctor doctor = new Doctor( 5030524268902L, "Mihai", "Alexandru", 'M', "+441234567890", "mihai.alexandru@yahoo.com","109B, Palace Street");

        doctorDAO.insert(doctor);

        // Select all the patients
        doctorList = doctorDAO.select();

        System.out.println("\nAll doctors (AFTER INSERTION):");
        for (Doctor doctor1 : doctorList) {
            System.out.println("\t" + doctor1);
        }

        doctorDAO.setFirstName(doctor.getCnp(), "Alex");
        doctorDAO.setLastName(doctor.getCnp(), "Alexandru");
        doctorDAO.setPhoneNumber(doctor.getCnp(), "+44-0000-000000");
        doctorDAO.setAddress(doctor.getCnp(), "14, Constitution Hill");

        // Select all the patients
        doctorList = doctorDAO.select();

        System.out.println("\nAll patients (After the changes:");
        for (Doctor doctor1 : doctorList) {
            System.out.println("\t" + doctor1);
        }

        System.out.println("Is there a patient with CNP '5030524268901L'? " + doctorDAO.exists(5030524268901L));
        doctorDAO.delete(5030524268901L);
        System.out.println("Is there a patient with CNP '5030524268901L'? " + doctorDAO.exists(5030524268901L));

    }

    private void testPatientDAO(Connection connection) throws SQLException {
        PatientDAO patientDAO = new PatientDAO(connection);

        // Select all the patients
        List<Patient> patientList = new ArrayList<>();

        // Insert a new Patient
        Patient patient1 = new Patient( 5030524268901L, "Claudiu", "Chichirau", 'M', "+441234567890", "claudiu.chichirau@yahoo.com","109B, Palace Street");
        Patient patient2 = new Patient( 5031124264501L, "Matei", "Prisacariu", 'M', "+441575674701", "matei.prisacariu@yahoo.ro","111D, Palace Street");
        Patient patient3 = new Patient( 5031121835501L, "Alexandra", "Olteanu", 'F', "+446276204730", "alexandru_olteanu@gmail.com","23A, Palace Street");

        patientDAO.insert(patient1);
        patientDAO.insert(patient2);
        patientDAO.insert(patient3);

        // Select all the patients
        patientList = patientDAO.select();

        System.out.println("\nAll patients (AFTER INSERTION):");
        for (Patient patient : patientList) {
            System.out.println("\t" + patient);
        }

        patientDAO.setFirstName(5030524268901L, "Alex");
        patientDAO.setLastName(5030524268901L, "Alexandru");
        patientDAO.setPhoneNumber(5030524268901L, "+44-0000-000000");
        patientDAO.setAddress(5030524268901L, "14, Constitution Hill");

        // Select all the patients
        patientList = patientDAO.select();

        System.out.println("\nAll patients (After the changes:");
        for (Patient patient : patientList) {
            System.out.println("\t" + patient);
        }

        System.out.println("Is there a patient with CNP '5030524268901L'? " + patientDAO.existsByCNP(5030524268901L));
//        patientDAO.delete(5030524268901L);
        System.out.println("Is there a patient with CNP '5030524268901L'? " + patientDAO.existsByCNP(5030524268901L));

    }

    public void testUserAuthenticationDAO(Connection connection) throws SQLException {
        UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(connection);

        // Insert a new Patient
        Patient patient1 = new Patient("claudiu.chichirau@yahoo.com", "parolaluiClaudiu1");
        Patient patient2 = new Patient("matei.prisacariu@yahoo.ro", "parolaluiMatei2");
        Patient patient3 = new Patient("alexandru_olteanu@gmail.com", "parolaluAlexandrei2");
        Patient admin = new Patient("admin@admin.com", "Admin1234");

//        userAuthenticationDAO.insert(patient1);
//        userAuthenticationDAO.insert(patient2);
//        userAuthenticationDAO.insert(patient3);

        userAuthenticationDAO.insert(admin);

        System.out.println("\nParola: " + userAuthenticationDAO.getPassword("claudiu.chichirau@yahoo.com"));
        System.out.println("\nSalt: " + userAuthenticationDAO.getSalt("claudiu.chichirau@yahoo.com"));

        userAuthenticationDAO.setPassword("claudiu.chichirau@yahoo.com", "Abcdefghij1");

        System.out.println("\nParola: " + userAuthenticationDAO.getPassword("claudiu.chichirau@yahoo.com"));
        System.out.println("\nSalt: " + userAuthenticationDAO.getSalt("claudiu.chichirau@yahoo.com"));

        // Select all the patients
        List<Person> patientList = new ArrayList<>();

        patientList = userAuthenticationDAO.select();

        System.out.println("\nAll patients (AFTER INSERTION):");
        for (Person person : patientList) {
            System.out.println("\t" + person);
        }

        // Delete the pacient
//        userAuthenticationDAO.delete("claudiu.chichirau@yahoo.com");

        patientList = userAuthenticationDAO.select();

        System.out.println("\nAll patients (AFTER DELETION):");
        for (Person person : patientList) {
            System.out.println("\t" + person);
        }
    }
}
