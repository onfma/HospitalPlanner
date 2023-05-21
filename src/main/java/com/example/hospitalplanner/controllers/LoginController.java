package com.example.hospitalplanner.controllers;

import com.example.hospitalplanner.database.DAO.AdminDAO;
import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAO.UserAuthenticationDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.exceptions.AuthenticationException;
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
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private HttpSession session;

    @GetMapping
    public String showLoginPage() {
        System.out.println("S-a afisat pagina loginPage.hmtl!");
        return "loginPage";
    }

    @PostMapping
    public String processLoginForm(@RequestParam("email") String email, @RequestParam("pswd") String password) throws SQLException, AuthenticationException {

        DAOFactory daoFactory = new DAOFactory();
        UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());

        if(userAuthenticationDAO.exists(email) == true) {


            String hashedPassword = hashPassword(password, userAuthenticationDAO.getSalt(email));

            if(hashedPassword.equals(userAuthenticationDAO.getPassword(email))) {
                // Store the user's information in the session
                session.setAttribute("email", email);

                // user succesfully connected
                PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
                DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
                AdminDAO adminDAO = new AdminDAO(daoFactory.getConnection());

                if(patientDAO.existsByEmail(email) && !doctorDAO.existsByEmail(email) && !adminDAO.existsByEmail(email)) {
                    // redirect to PATIENT dashboard
                    System.out.println("It's a PATIENT!");
                    return "redirect:/patientDashboard";
                } else if (!patientDAO.existsByEmail(email) && doctorDAO.existsByEmail(email) && !adminDAO.existsByEmail(email)) {
                    // redirect to DOCTOR dashboard
                    System.out.println("It's a DOCTOR!");
                    return "redirect:/doctorDashboard";
                } else if (!patientDAO.existsByEmail(email) && !doctorDAO.existsByEmail(email) && adminDAO.existsByEmail(email)) {
                    // redirect to ADMIN dashboard
                    System.out.println("It's an ADMIN!");
                    return "redirect:/adminDashboard";
                }
            }
            else {
                throw new AuthenticationException("You entered the wrong password or email address!\nPlease fill in again!");
            }
        }
        else {
            throw new AuthenticationException("You entered the wrong password or email address!\nPlease fill in again!");
        }
        return "redirect:/dashboard";
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
