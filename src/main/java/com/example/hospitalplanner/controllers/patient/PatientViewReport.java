package com.example.hospitalplanner.controllers.patient;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/viewReport")
public class PatientViewReport {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showViewReport() {
        System.out.println("S-a afisat pagina patientViewReport.hmtl!");
        return "patient/patientViewReport"; // redirect to homepage
    }
}