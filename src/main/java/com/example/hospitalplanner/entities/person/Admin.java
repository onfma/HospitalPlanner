package com.example.hospitalplanner.entities.person;

import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;

import java.util.Collections;
import java.util.List;

public class Admin {
    private String email;
    private String password;
    private final List<Patient> patientList;
    private final List<Doctor> doctorList;
    private List<Cabinet> cabinetList;
    private List<CabinetSchedule> cabinetSchedulesList;
    private final List<DoctorSchedule> doctorScheduleList;
    private final List<Appoinments> appoinmentsList;

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
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public final List<Patient> getPatientList() {
        return patientList;
    }

    public final List<Doctor> getDoctorList() {
        return doctorList;
    }

    public List<Patient> removePatient(Patient patient) {
        this.patientList.remove(patient);
        return this.patientList;
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
        this.cabinetSchedulesList.add(cabinetSchedule);
    }

    public void removeCabinetSchedule(CabinetSchedule cabinetSchedule) {
        this.cabinetSchedulesList.remove(cabinetSchedule);
    }

    // ce poate sa faca adminul?

// X    // sa vizualizeze datele pacientilor, doctorilor, programul doctorilor FARA a le edita (sterge, etc)
// X        // poate doar sa stearga un doctor / pacient, fara a crea unul
// X        // nu poate sa editeze datele unui doctor/pacient
// X    // poate sa vada programarile pacientilor + sa le stearga
// X    // sa editeze Cabinete (adauga, sterge)
// X    // sa modifice CabinetSchedule
}
