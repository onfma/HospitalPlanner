package com.example.hospitalplanner.models;

import java.time.LocalDateTime;

public class AppointmentModel {
    private String doctorFirstName;
    private String doctorLastName;
    private String specialityName;
    private LocalDateTime appointmentTime;


    public AppointmentModel(String doctorFirstName, String doctorLastName, String specialityName, LocalDateTime appointmentTime) {
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.specialityName = specialityName;
        this.appointmentTime = appointmentTime;
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
}