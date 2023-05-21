package com.example.hospitalplanner.controllers.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminModifyPatient")
public class AdminModifyPatientController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showAdminDashboard() {
        System.out.println("S-a afisat pagina adminModifyPatient.hmtl!");
        return "adminModifyPatient";
    }
}
