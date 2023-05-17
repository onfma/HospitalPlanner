package com.example.hospitalplanner.entities.person;

import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;

import java.util.Collections;
import java.util.List;

public class Admin extends Person {
    private String email;
    private String password;
    private List<Patient> patientList;
    private List<Doctor> doctorList;
    private List<Cabinet> cabinetList;
    private List<CabinetSchedule> cabinetSchedulesList;
    private List<DoctorSchedule> doctorScheduleList;
    private List<Appoinments> appoinmentsList;

    public Admin() {}

    public Admin(String email, String password) {
        super(email, password);
    }

    public Admin(List<Patient> patientList, List<Doctor> doctorList, List<Cabinet> cabinetList, List<CabinetSchedule> cabinetSchedulesList, List<DoctorSchedule> doctorScheduleList, List<Appoinments> appoinmentsList) {
        this.patientList = Collections.unmodifiableList(patientList);
        this.doctorList = Collections.unmodifiableList(doctorList);
        this.cabinetList = cabinetList;
        this.cabinetSchedulesList = cabinetSchedulesList;
        this.doctorScheduleList = Collections.unmodifiableList(doctorScheduleList);
        this.appoinmentsList = Collections.unmodifiableList(appoinmentsList);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // Check the email format
        String emailRegex = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Email format is invalid.");
        }

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // Check password composition
        boolean containsUpperCase = false;
        boolean containsLowerCase = false;
        boolean containsDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                containsLowerCase = true;
            } else if (Character.isDigit(c)) {
                containsDigit = true;
            }
        }

        if(password.length() < 8)
            throw new IllegalArgumentException("Password must have at least 8 characters!");

        if (!containsUpperCase || !containsLowerCase || !containsDigit) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, and one digit!");
        }

        this.password = password;
    }

    public final List<Patient> getPatientList() {
        return patientList;
    }

    public final List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void removePatient(Patient patient) {
        this.patientList.remove(patient);
    }

    public void removeDoctor(Doctor doctor) {
        this.doctorList.remove(doctor);
    }

    public void removeAppoinment(Appoinments appoinment) {
        this.appoinmentsList.remove(appoinment);
    }

    public List<Cabinet> getCabinetList() {
        return cabinetList;
    }

    public void setCabinetList(List<Cabinet> cabinetList) {
        this.cabinetList = cabinetList;
    }

    public void addCabinet(Cabinet cabinet) {
        if (cabinet == null)
            throw new IllegalArgumentException("Cabinet cannot be null");
        if (cabinetList.contains(cabinet))
            throw new IllegalArgumentException("Cabinet already exists in the cabinetList list");

        this.cabinetList.add(cabinet);
    }

    public void removeCabinet (Cabinet cabinet) {
        this.cabinetList.remove(cabinet);
    }

    public List<CabinetSchedule> getCabinetSchedulesList() {
        return cabinetSchedulesList;
    }

    public void setCabinetSchedulesList(List<CabinetSchedule> cabinetSchedulesList) {
        this.cabinetSchedulesList = cabinetSchedulesList;
    }

    public void addCabinetSchedule(CabinetSchedule cabinetSchedule) {
        if (cabinetSchedule == null)
            throw new IllegalArgumentException("CabinetSchedule cannot be null");
        if (cabinetSchedulesList.contains(cabinetSchedule))
            throw new IllegalArgumentException("CabinetSchedule already exists in the cabinetSchedulesList list");

        this.cabinetSchedulesList.add(cabinetSchedule);
    }

    public void removeCabinetSchedule(CabinetSchedule cabinetSchedule) {
        this.cabinetSchedulesList.remove(cabinetSchedule);
    }

//       what can the admin do?

//    X // to view the data of patients, doctors, doctors' schedule WITHOUT editing them (delete, etc.)
//    X // can only delete a doctor / patient, without creating one
//    X // cannot edit the data of a doctor/patient
//    X // can see patients' appointments + delete them
//    X // to edit Cabinets (add, delete)
//    X // to modify CabinetSchedule
}
