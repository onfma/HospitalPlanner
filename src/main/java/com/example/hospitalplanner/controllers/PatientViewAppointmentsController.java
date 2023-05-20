package com.example.hospitalplanner.controllers;

import com.example.hospitalplanner.database.DAO.AppointmentsDAO;
import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAOFactory;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.hospitalplanner.entities.Appoinments;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/myAppointments")
public class PatientViewAppointmentsController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showMyAppointmentsPage() throws SQLException {
        System.out.println("S-a afisat pagina patientAppointments.hmtl!");

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());

        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");

        long cnp = 0;
        if(patientDAO.existsByEmail(personEmail) == true)  // it's a patient
            cnp = patientDAO.getCNP(personEmail);
        else if (doctorDAO.existsByEmail(personEmail) == true)
            cnp = doctorDAO.getCNP(personEmail);

        // Interoghează baza de date pentru a obține programările pacienților
        List<Appoinments> appointments = appointmentsDAO.getPatientAppointments(cnp);

        // Adaugă lista de programări în model
        session.setAttribute("appointments", appointments);

        return "patientViewAppointments";
    }


}