package com.example.hospitalplanner.entities.person;

import com.example.hospitalplanner.entities.Appoinments;

import java.util.List;

public abstract class Person {
    protected int cnp;
    protected String firstName;
    protected String lastName;
    protected String gender;
    protected String phoneNumber;
    protected String address;
    protected String email;
    protected String password;
    protected List<Appoinments> appoinments;

    // Constructor

    public Person(){}

    public Person(String email, String password){
        // Check the email format
        String emailRegex = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Email format is invalid.");
        }

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

        // Variable assignment
        this.email = email;
        this.password = password;
    }

    public Person(int cnp, String firstName, String lastName, String gender, String phoneNumber, String address) {
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and setters


    public int getCnp() {
        return cnp;
    }

    public void setCnp(int cnp) {
        this.cnp = cnp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Appoinments> getAppoinments() {
        return appoinments;
    }

    public void setAppoinments(List<Appoinments> appoinments) {
        this.appoinments = appoinments;
    }

    public void addAppoinments(Appoinments appoinment){
        appoinments.add(appoinment);
    }

    public void removeAppoinments(Appoinments appoinment){
        appoinments.remove(appoinment);
    }
}
