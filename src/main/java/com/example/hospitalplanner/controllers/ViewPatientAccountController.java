package com.example.hospitalplanner.controllers;

import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAO.UserAuthenticationDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.exceptions.AuthenticationException;
import com.example.hospitalplanner.exceptions.ChangeAccountException;
import com.example.hospitalplanner.exceptions.ChangeAccountSuccess;
import com.example.hospitalplanner.exceptions.CreateAccountException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;


@Controller
@RequestMapping("/patientViewAccount")
public class ViewPatientAccountController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showViewPatientAccountPage() {
        System.out.println("S-a afisat pagina patientViewAccount.hmtl!");
        return "patientViewAccount";
    }

    @PostMapping
    public String processChangeAccountForm(@RequestParam("first_name") String newFirstName,
                                           @RequestParam("last_name") String newLastName,
                                           @RequestParam("tel") String newPhoneNumber,
                                           @RequestParam("email") String newEmail,
                                           @RequestParam("home_address") String newAddress,
                                           @RequestParam("pswd") String newPassword) throws SQLException, ChangeAccountException, ChangeAccountSuccess {

        DAOFactory daoFactory = new DAOFactory();

        // verify if it's patient or doctor
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");

        if(patientDAO.existsByEmail(personEmail) == true) { // it's a patient
            long cnp = patientDAO.getCNP(personEmail);

            if(!newFirstName.equals("")) // verify if it's the same FirstName as in DB
                if(newFirstName.equals(patientDAO.getFirstName(cnp)))
                    throw new ChangeAccountException("You entered the same 'First Name' field!\nPlease fill in again!");

            if(!newLastName.equals(""))  // verify if it's the same LastName as in DB
                if(newLastName.equals(patientDAO.getLastName(cnp)))
                    throw new ChangeAccountException("You entered the same 'Last Name' field!\nPlease fill in again!");

            if(!newPhoneNumber.equals("")) // change phoneNumber // verify if it's the same LastName as in DB
                if(newPhoneNumber.equals(patientDAO.getPhoneNumber(cnp)))
                    throw new ChangeAccountException("You entered the same 'Phone Number' field!\nPlease fill in again!");

            // TODO for email

            if(!newAddress.equals(""))  // verify if it's the same address as in DB
                if(newAddress.equals(patientDAO.getAddress(cnp)))
                    throw new ChangeAccountException("You entered the same 'Address' field!\nPlease fill in again!");

            if(!newPassword.equals("")) { // verify if it's the same password as in DB
                UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());

                String hashedPassword = hashPassword(newPassword, userAuthenticationDAO.getSalt(personEmail));

                if (hashedPassword.equals(userAuthenticationDAO.getPassword(personEmail)))
                    throw new ChangeAccountException("You entered the same 'Password' field!\nPlease fill in again!");
            }

            /// change fields

            if(!newFirstName.equals("")) // change FirstName
                patientDAO.setFirstName(cnp, newFirstName);

            if(!newLastName.equals("")) // change LastName
                patientDAO.setLastName(cnp, newLastName);

            if(!newPhoneNumber.equals(""))  // change phoneNumber
                patientDAO.setPhoneNumber(cnp, newPhoneNumber);

            // TODO for email

            if(!newAddress.equals("")) // change address
                patientDAO.setAddress(cnp, newAddress);

            if(!newPassword.equals("")) { // change password
                UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());

                userAuthenticationDAO.setPassword(personEmail, newPassword);
            }

            throw new ChangeAccountSuccess("Your account information has been successfully updated!");
        } else if (doctorDAO.existsByEmail(personEmail) == true) { // it's a doctor

        }


        return "redirect:/patientViewAccount"; // redirect to homepage

//        return null;
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