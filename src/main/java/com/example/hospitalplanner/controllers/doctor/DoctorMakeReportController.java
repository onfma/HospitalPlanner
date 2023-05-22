package com.example.hospitalplanner.controllers.doctor;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctorMakeReport")
public class DoctorMakeReportController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showDoctorDashboard() {
        System.out.println("S-a afisat pagina doctorMakeReport.html! E conectat: " + session.getAttribute("email"));
        return "doctor/doctorMakeReport";
    }
}
