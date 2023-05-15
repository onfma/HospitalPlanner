package com.example.hospitalplanner.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String showLoginPage() {
        System.out.println("S-a afisat pagina!");
        return "loginPage";
    }

    @PostMapping
    public String processLoginForm(@RequestParam("email") String email, @RequestParam("pswd") String password) {
        System.out.println("Email: " + email + ", parola: " + password);

        // Logică pentru procesarea datelor de autentificare
        // Aici poți valida credențialele, să verifici autentificarea și să returnezi o pagină de succes sau de eroare
        return "redirect:/dashboard"; // Exemplu de redirecționare către o pagină de bord
    }
}
