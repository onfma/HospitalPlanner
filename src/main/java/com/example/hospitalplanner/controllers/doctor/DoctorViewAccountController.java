package com.example.hospitalplanner.controllers.doctor;


import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/doctorViewAccount")
public class DoctorViewAccountController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showDoctorDashboard(Model model) throws SQLException {
        System.out.println("S-a afisat pagina doctorDashboard.html.hmtl!");

        DAOFactory daoFactory = new DAOFactory();
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");
        long cnp = doctorDAO.getCNP(personEmail);

        Doctor doctor = new Doctor();

        doctor.setCnp(cnp);
        doctor.setFirstName(doctorDAO.getFirstName(cnp));
        doctor.setLastName(doctorDAO.getLastName(cnp));
        doctor.setGender(doctorDAO.getGender(cnp));
        doctor.setPhoneNumber(doctorDAO.getPhoneNumber(cnp));
        doctor.setAddress(doctorDAO.getAddress(cnp));
        doctor.setEmail(doctorDAO.getEmail(cnp));

        // Add patient in model
        model.addAttribute("doctor", doctor);

        return "doctorViewAccount";
    }
}
