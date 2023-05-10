package com.example.hospitalplanner.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Appoinments {
    private int id;
    private int cabinetID;
    private int doctorCNP;
    private int patientCNP;
    private LocalDateTime appointmentTime;

    public Appoinments(int id, int cabinetID, int doctorCNP, int patientCNP, LocalDateTime appointmentTime){
        this.id = id;
        this.cabinetID = cabinetID;
        this.doctorCNP = doctorCNP;
        this.patientCNP = patientCNP;
        this.appointmentTime = appointmentTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCabinetID() {
        return cabinetID;
    }

    public void setCabinetID(int cabinetID) {
        this.cabinetID = cabinetID;
    }

    public int getDoctorCNP() {
        return doctorCNP;
    }

    public void setDoctorCNP(int doctorCNP) {
        this.doctorCNP = doctorCNP;
    }

    public int getPatientCNP() {
        return patientCNP;
    }

    public void setPatientCNP(int patientCNP) {
        this.patientCNP = patientCNP;
    }

    public LocalDateTime getAppointmenTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appoinments that = (Appoinments) o;
        return id == that.id && cabinetID == that.cabinetID && doctorCNP == that.doctorCNP && patientCNP == that.patientCNP && Objects.equals(appointmentTime, that.appointmentTime);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", cabinet_id=" + cabinetID +
                ", doctor_CNP=" + doctorCNP +
                ", patient_CNP=" + patientCNP +
                ", appointment_time=" + appointmentTime +
                '}';
    }

}
