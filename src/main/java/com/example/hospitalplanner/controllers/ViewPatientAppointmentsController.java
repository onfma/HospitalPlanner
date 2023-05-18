package com.example.hospitalplanner.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myAppointments")
public class ViewPatientAppointmentsController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showMyAppointmentsPage() {
        System.out.println("S-a afisat pagina patientAppointments.hmtl!");
        return "redirect:/"; // redirect to homepage
    }
}