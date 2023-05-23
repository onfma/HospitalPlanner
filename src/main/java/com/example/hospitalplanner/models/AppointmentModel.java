package com.example.hospitalplanner.models;

import java.time.LocalDateTime;

public class AppointmentModel {
    private int id;
    private String cabinetName;
    private String examinationType;
    private String patientFirstName;
    private String patientLastName;
    private String doctorFirstName;
    private String doctorLastName;
    private String specialityName;
    private LocalDateTime appointmentTime;
    private String diagnosis;
    private String treatment;
    private String formattedDate;
    private String formattedTime;
    private long patientCNP;
    private char patientGender;

    public AppointmentModel() {}

    public AppointmentModel(int id, String doctorFirstName, String doctorLastName, String specialityName, LocalDateTime appointmentTime) {
        this.id = id;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.specialityName = specialityName;
        this.appointmentTime = appointmentTime;
    }

    public AppointmentModel(int id, String cabinetName, String examinationType, String doctorFirstName, String doctorLastName, LocalDateTime appointmentTime, String diagnosis, String treatment) {
        this.id = id;
        this.cabinetName = cabinetName;
        this.examinationType = examinationType;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.appointmentTime = appointmentTime;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public String getExaminationType() {
        return examinationType;
    }

    public void setExaminationType(String examinationType) {
        this.examinationType = examinationType;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public long getPatientCNP() {
        return patientCNP;
    }

    public void setPatientCNP(long patientCNP) {
        this.patientCNP = patientCNP;
    }

    public char getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(char patientGender) {
        this.patientGender = patientGender;
    }
}