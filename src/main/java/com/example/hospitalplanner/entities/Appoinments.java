package com.example.hospitalplanner.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Appoinments {
    private int id;
    private int cabinetID;
    private long doctorCNP;
    private long patientCNP;
    private LocalDateTime appointmentTime;
    private int duration;
    private String diagnosis;
    private String treatment;
    private int examinationID;


    public Appoinments() {}
    public Appoinments(int id, int cabinetID, long doctorCNP, long patientCNP, LocalDateTime appointmentTime, int examinationID){
        this.id = id;
        this.cabinetID = cabinetID;
        this.doctorCNP = doctorCNP;
        this.patientCNP = patientCNP;
        this.appointmentTime = appointmentTime;
        this.examinationID = examinationID;
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

    public long getDoctorCNP() {
        return doctorCNP;
    }

    public void setDoctorCNP(long doctorCNP) {
        this.doctorCNP = doctorCNP;
    }

    public long getPatientCNP() {
        return patientCNP;
    }

    public void setPatientCNP(long patientCNP) {
        this.patientCNP = patientCNP;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public int getExaminationID() {
        return examinationID;
    }

    public void setExaminationID(int examinationID) {
        this.examinationID = examinationID;
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
