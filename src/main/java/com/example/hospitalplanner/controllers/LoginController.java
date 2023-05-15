package com.example.hospitalplanner.controllers;

import com.example.hospitalplanner.database.DAO.UserAuthenticationDAO;
import com.example.hospitalplanner.database.DAOFactory;
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
    public String processLoginForm(@RequestParam("email") String email, @RequestParam("pswd") String password) throws SQLException {

        DAOFactory daoFactory = new DAOFactory();
        UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());

        if(userAuthenticationDAO.exists(email) == true) {


            String hashedPassword = hashPassword(password, userAuthenticationDAO.getSalt(email));

            if(hashedPassword.equals(userAuthenticationDAO.getPassword(email)))
                System.out.println("Datele de autentificare sunt corecte!");
            else
                System.out.println("Datele de autentificare sunt GRESITE!\n\tEmail: " + email + ", parola introdusa: " + hashedPassword + ", parola din db: " + userAuthenticationDAO.getPassword(email));
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
