package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAO.UserAuthenticationDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.exceptions.ChangeAccountException;
import com.example.hospitalplanner.exceptions.ChangeAccountSuccess;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class PatientViewAccountController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showViewPatientAccountPage(Model model) throws SQLException {
        System.out.println("S-a afisat pagina patientViewAccount.hmtl!");

        DAOFactory daoFactory = new DAOFactory();
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");
        long cnp = patientDAO.getCNP(personEmail);

        Patient patient = new Patient();

        patient.setCnp(cnp);
        patient.setFirstName(patientDAO.getFirstName(cnp));
        patient.setLastName(patientDAO.getLastName(cnp));
        patient.setGender(patientDAO.getGender(cnp));
        patient.setPhoneNumber(patientDAO.getPhoneNumber(cnp));
        patient.setAddress(patientDAO.getAddress(cnp));
        patient.setEmail(patientDAO.getEmail(cnp));

        // Add patient in model
        model.addAttribute("patient", patient);

        return "patient/patientViewAccount";
    }

    @PostMapping
    public String processChangeAccountForm(@RequestParam("first_name") String newFirstName,
                                           @RequestParam("last_name") String newLastName,
                                           @RequestParam("tel") String newPhoneNumber,
                                           @RequestParam("email") String newEmail,
                                           @RequestParam("home_address") String newAddress,
                                           @RequestParam("pswd") String newPassword,
                                           Model model) throws SQLException, ChangeAccountException, ChangeAccountSuccess {

        DAOFactory daoFactory = new DAOFactory();

        // verify if it's patient or doctor
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");

        if(patientDAO.existsByEmail(personEmail) == true) { // it's a patient
            long cnp = patientDAO.getCNP(personEmail);
            UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());

            if(!newFirstName.equals("")) // change FirstName
                patientDAO.setFirstName(cnp, newFirstName);

            if(!newLastName.equals("")) // change LastName
                patientDAO.setLastName(cnp, newLastName);

            if(!newPhoneNumber.equals(""))  // change phoneNumber
                patientDAO.setPhoneNumber(cnp, newPhoneNumber);

            if(!newEmail.equals("")) { // change Email
                if(!userAuthenticationDAO.exists(newEmail)) { // if email doesn't exist in DB
                    // insert a new row in "UserAuthentication" table with the new email
                    Patient patient = new Patient();

                    patient.setEmail(newEmail);
                    patient.setPasswordController(userAuthenticationDAO.getPassword(personEmail));
                    patient.setSalt(userAuthenticationDAO.getSalt(personEmail));

                    userAuthenticationDAO.insert(patient);

                    // update teh email in "Pacient" table
                    patientDAO.setEmail(cnp, newEmail);

                    // delete the old email from "UserAuthentication" table
                    userAuthenticationDAO.delete(personEmail);

                    // update email in "session" field
                    session.setAttribute("email", newEmail);
                }
            }

            if(!newAddress.equals("")) // change address
                patientDAO.setAddress(cnp, newAddress);

            if(!newPassword.equals("")) // change password
                userAuthenticationDAO.setPassword(personEmail, newPassword);

//            throw new ChangeAccountSuccess("Your account information has been successfully updated!");

        }


        return showViewPatientAccountPage(model); // redirect to patient dashboard
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