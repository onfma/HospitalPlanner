package com.example.hospitalplanner.exceptions;

public class MakeAppointmentException extends Exception {
    private int cabinetId;

    public MakeAppointmentException(String message, int cabinetId) {
        super(message);
        this.cabinetId = cabinetId;
    }

    public int getCabinetId() {
        return cabinetId;
    }
}
