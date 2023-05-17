package com.example.hospitalplanner.controllers;

import com.example.hospitalplanner.database.DAO.AdminDAO;
import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAO.UserAuthenticationDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.exceptions.AuthenticationException;
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

    @GetMapping
    public String showLoginPage() {
        System.out.println("S-a afisat pagina!");
        return "loginPage";
    }

    @PostMapping
    public String processLoginForm(@RequestParam("email") String email, @RequestParam("pswd") String password) throws SQLException, AuthenticationException {

        DAOFactory daoFactory = new DAOFactory();
        UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());

        if(userAuthenticationDAO.exists(email) == true) {


            String hashedPassword = hashPassword(password, userAuthenticationDAO.getSalt(email));

            if(hashedPassword.equals(userAuthenticationDAO.getPassword(email))) {
                // user succesfully connected

                PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
                DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
                AdminDAO adminDAO = new AdminDAO(daoFactory.getConnection());

                if(patientDAO.existsByEmail(email) && !doctorDAO.existsByEmail(email) && !adminDAO.existsByEmail(email)) {
                    // redirect to PATIENT dashboard
                    System.out.println("It's a PATIENT!");
                } else if (!patientDAO.existsByEmail(email) && doctorDAO.existsByEmail(email) && !adminDAO.existsByEmail(email)) {
                    // redirect to DOCTOR dashboard
                    System.out.println("It's a DOCTOR!");
                } else if (!patientDAO.existsByEmail(email) && !doctorDAO.existsByEmail(email) && adminDAO.existsByEmail(email)) {
                    // redirect to ADMIN dashboard
                    System.out.println("It's an ADMIN!");
                }
            }
            else {
                System.out.println("Parola este gresita!\n\tEmail: " + email + ", parola introdusa: " + hashedPassword + ", parola din db: " + userAuthenticationDAO.getPassword(email));
                throw new AuthenticationException("You entered the wrong password or email address!\nPlease fill in again!");
            }
        }
        else {
            System.out.println("Email-ul introdus este gresit!\n\tEmail: " + email);
            throw new AuthenticationException("You entered the wrong password or email address!\nPlease fill in again!");
        }

        // Logică pentru procesarea datelor de autentificare
        // Aici poți valida credențialele, să verifici autentificarea și să returnezi o pagină de succes sau de eroare
        return "redirect:/dashboard"; // Exemplu de redirecționare către o pagină de bord
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
