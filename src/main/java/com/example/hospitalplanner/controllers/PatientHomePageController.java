package com.example.hospitalplanner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/patientDashboard")
public class PatientHomePageController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showLoginPage() {
        // Access the session information
        String email = (String) session.getAttribute("email");

        System.out.println("S-a afisat pagina patientHomePage.hmtl! Este conectat clientul cu email-ul:" + email);
        return "patientDashboard";
    }
}
