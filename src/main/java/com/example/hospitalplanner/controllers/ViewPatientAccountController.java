package com.example.hospitalplanner.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/patientViewAccount")
public class ViewPatientAccountController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showLoginPage() {
        System.out.println("S-a afisat pagina patientViewAccount.hmtl!");
        return "patientViewAccount";
    }
}