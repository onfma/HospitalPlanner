package com.example.hospitalplanner.controllers.doctor;


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
@RequestMapping("/doctorViewAccount")
public class DoctorViewAccountController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showDoctorDashboard(Model model) throws SQLException {
        System.out.println("S-a afisat pagina doctorDashboard.html.hmtl!");

        DAOFactory daoFactory = new DAOFactory();
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");
        long cnp = doctorDAO.getCNP(personEmail);

        Doctor doctor = new Doctor();

        doctor.setCnp(cnp);
        doctor.setFirstName(doctorDAO.getFirstName(cnp));
        doctor.setLastName(doctorDAO.getLastName(cnp));
        doctor.setGender(doctorDAO.getGender(cnp));
        doctor.setPhoneNumber(doctorDAO.getPhoneNumber(cnp));
        doctor.setAddress(doctorDAO.getAddress(cnp));
        doctor.setEmail(doctorDAO.getEmail(cnp));

        // Add patient in model
        model.addAttribute("doctor", doctor);

        return "doctorViewAccount";
    }

    @PostMapping
    public String processChangeAccountForm(@RequestParam("first_name") String newFirstName,
                                           @RequestParam("last_name") String newLastName,
                                           @RequestParam("tel") String newPhoneNumber,
                                           @RequestParam("email") String newEmail,
                                           @RequestParam("home_address") String newAddress,
                                           @RequestParam("pswd") String newPassword,
                                           Model model) throws SQLException {

        System.out.println("Am intrat sa editez contul doctorului!");

        DAOFactory daoFactory = new DAOFactory();

        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");

        if (doctorDAO.existsByEmail(personEmail) == true) { // it's a doctor
            long cnp = doctorDAO.getCNP(personEmail);

            UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());
            /// change fields

            if(!newFirstName.equals("")) // change FirstName
                doctorDAO.setFirstName(cnp, newFirstName);

            if(!newLastName.equals("")) // change LastName
                doctorDAO.setLastName(cnp, newLastName);

            if(!newPhoneNumber.equals(""))  // change phoneNumber
                doctorDAO.setPhoneNumber(cnp, newPhoneNumber);

            if(!newEmail.equals("")) { // change Email
                // insert a new row in "UserAuthentication" table with the new email
                Doctor doctor = new Doctor();

                doctor.setEmail(newEmail);
                doctor.setPasswordController(userAuthenticationDAO.getPassword(personEmail));
                doctor.setSalt(userAuthenticationDAO.getSalt(personEmail));

                userAuthenticationDAO.insert(doctor);

                // update teh email in "Doctor" table
                doctorDAO.setEmail(cnp, newEmail);

                // delete the old email from "UserAuthentication" table
                userAuthenticationDAO.delete(personEmail);

                // update email in "session" field
                session.setAttribute("email", newEmail);
            }

            if(!newAddress.equals("")) // change address
                doctorDAO.setAddress(cnp, newAddress);

            if(!newPassword.equals("")) // change password
                userAuthenticationDAO.setPassword(personEmail, newPassword);
        }
        System.out.println("Am iesit sa editez contul doctorului!");
//        return "doctorViewAccount";
        return showDoctorDashboard(model); // redirect to doctor dashboard
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
