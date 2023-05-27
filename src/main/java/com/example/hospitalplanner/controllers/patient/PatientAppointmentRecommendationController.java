package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.entities.Appoinments;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.ArrayList;

import java.sql.SQLException;

@Controller
@RequestMapping("/patientAppointmentRecommendation")
public class PatientAppointmentRecommendationController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showPatientAppointmentRecommendationPage(Model model) throws SQLException {

        List<Appoinments> appoinmentSlots = (List<Appoinments>) model.getAttribute("appoinmentSlots");

        System.out.println("Posibile programari: " + appoinmentSlots);

//        model.addAttribute("patient", patient);

        return "patient/patientAppointmentRecommandation";
    }
}