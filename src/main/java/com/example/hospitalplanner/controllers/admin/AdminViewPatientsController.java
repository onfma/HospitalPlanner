package com.example.hospitalplanner.controllers.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminViewPatients")
public class AdminViewPatientsController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showAdminDashboard() {
        System.out.println("S-a afisat pagina adminViewPatients.hmtl!");
        return "adminViewPatients";
    }
}
