package com.example.hospitalplanner.controllers.patient;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/makeAppointment")
public class PatientMakeAppointmentController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showMakeAnAppointmentPage() {
        System.out.println("S-a afisat pagina makeAnAppointment.hmtl!");
        return "redirect:/"; // redirect to homepage
    }
}