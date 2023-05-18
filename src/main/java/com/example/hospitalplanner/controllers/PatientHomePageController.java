package com.example.hospitalplanner.controllers;

import ch.qos.logback.core.model.Model;
import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAOFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;


@Controller
@RequestMapping("/patientDashboard")
public class PatientHomePageController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public ModelAndView showPatientDashboardPage(ModelAndView modelAndView) throws SQLException {
        DAOFactory daoFactory = new DAOFactory();

        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        String doctorsJson = doctorDAO.selectJSON();
        modelAndView.addObject("json", doctorsJson);
        modelAndView.setViewName("patientDashboard");

        System.out.println("S-a afisat pagina patientHomePage.hmtl! Este conectat clientul cu email-ul:" + session.getAttribute("email"));

        return modelAndView;
    }
}
