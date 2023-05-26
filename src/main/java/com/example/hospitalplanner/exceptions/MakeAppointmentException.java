package com.example.hospitalplanner.exceptions;

public class MakeAppointmentException extends Exception {
    private String redirect;
    public MakeAppointmentException(String message, String redirect) {
        super(message);
        this.redirect = redirect;
    }
}