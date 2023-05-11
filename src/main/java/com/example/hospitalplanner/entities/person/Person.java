package com.example.hospitalplanner.entities.person;

import com.example.hospitalplanner.entities.Appoinments;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

public abstract class Person {
    protected long cnp;
    protected String firstName;
    protected String lastName;
    protected char gender;
    protected String phoneNumber;
    protected String address;
    protected String email;
    protected String password;
    protected String salt;
    protected List<Appoinments> appoinmentsList;

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

        // Generate salt
        String salt = generateSalt();

        // Hash password with salt
        String hashedPassword = hashPassword(password, salt);

        // Save salt and hashed password
        this.salt = salt;
        this.password = hashedPassword;
    }

    public Person(long cnp, String firstName, String lastName, char gender, String phoneNumber, String address) {
        if (String.valueOf(cnp).length() != 13)
            throw new IllegalArgumentException("Invalid CNP: CNP should have exactly 13 digits.");
        if (!firstName.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Invalid first name: First name should contain only letters.");
        if (!lastName.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Invalid last name: Last name should contain only letters.");
        if (!(gender == 'M') && !(gender == 'F'))
            throw new IllegalArgumentException("Invalid gender: Gender should be 'M' or 'F'.");
        if (!phoneNumber.matches("\\+44\\d{10}"))
            throw new IllegalArgumentException("Invalid phone number: Phone number should be in the format '+44xxxxxxxxxx' and should contains only digits.");
        if (!address.matches("[a-zA-Z0-9,. ;]+")) {
            throw new IllegalArgumentException("Invalid address: Address should contain only letters, digits, or the following characters: [,.;]");
        }


        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }


    public Person(long cnp, String firstName, String lastName, char gender, String phoneNumber, String address, List<Appoinments> appoinmentsList) {
        if (String.valueOf(cnp).length() != 13)
            throw new IllegalArgumentException("Invalid CNP: CNP should have exactly 13 digits.");
        if (!firstName.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Invalid first name: First name should contain only letters.");
        if (!lastName.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Invalid last name: Last name should contain only letters.");
        if (!(gender == 'M') && !(gender == 'F'))
            throw new IllegalArgumentException("Invalid gender: Gender should be 'M' or 'F'.");
        if (!phoneNumber.matches("\\+44\\d{10}"))
            throw new IllegalArgumentException("Invalid phone number: Phone number should be in the format '+44xxxxxxxxxx' and should contains only digits.");
        if (!address.matches("[a-zA-Z0-9,. ;]+")) {
            throw new IllegalArgumentException("Invalid address: Address should contain only letters, digits, or the following characters: [,.;]");
        }


        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.appoinmentsList = appoinmentsList;
    }


    // Getters and setters
    public long getCnp() {
        return cnp;
    }

    public void setCnp(long cnp) {
        this.cnp = cnp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (!firstName.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Invalid first name: First name should contain only letters.");

        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (!lastName.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Invalid last name: Last name should contain only letters.");

        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        if (!(gender == 'M') && !(gender == 'F'))
            throw new IllegalArgumentException("Invalid gender: Gender should be 'M' or 'F'.");

        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("\\+44-\\d{4}-\\d{6}"))
            throw new IllegalArgumentException("Invalid phone number: Phone number should be in the format '+44xxxxxxxxxx' and should contains only digits.");

        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9\\Q#$%^&*,.?~@[]{}+-_/=\\E]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$";

        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Email format is invalid.");
        }

        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!address.matches("[a-zA-Z0-9,. ;]+"))
            throw new IllegalArgumentException("Invalid address: Address should contain only letters, digits, or the following characters: [,.;]");

        this.address = address;
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

        // Generate salt
        String salt = generateSalt();

        // Hash password with salt
        String hashedPassword = hashPassword(password, salt);

        // Save salt and hashed password
        this.salt = salt;
        this.password = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public List<Appoinments> getAppoinmentsList() {
        return appoinmentsList;
    }

    public void setAppoinments(List<Appoinments> appoinmentsList) {
        if (appoinmentsList == null || appoinmentsList.contains(null))
            throw new IllegalArgumentException("Appoinments list cannot be null and cannot contain null values");

        this.appoinmentsList = appoinmentsList;
    }

    public void addAppoinments(Appoinments appoinment){
        if (appoinment == null)
            throw new IllegalArgumentException("Appoinment cannot be null");
        if (appoinmentsList.contains(appoinment))
            throw new IllegalArgumentException("Appoinment already exists in the appoinments list");

        appoinmentsList.add(appoinment);
    }

    public void removeAppoinments(Appoinments appoinment){
        if (appoinment == null)
            throw new IllegalArgumentException("Appoinment cannot be null");

        appoinmentsList.remove(appoinment);
    }

    private String generateSalt() {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    private String hashPassword(String password, String salt) {
        // Hash password with salt
        String passwordAndSalt = password + salt;
        String hashedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPasswordBytes = md.digest(passwordAndSalt.getBytes());
            hashedPassword = Base64.getEncoder().encodeToString(hashedPasswordBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }
}
