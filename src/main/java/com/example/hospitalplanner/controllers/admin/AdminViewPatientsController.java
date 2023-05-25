package com.example.hospitalplanner.controllers.admin;

import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.person.Patient;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/adminViewPatients")
public class AdminViewPatientsController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showAdminDashboard(Model model) throws SQLException {
        System.out.println("S-a afisat pagina adminViewPatients.hmtl!");

        DAOFactory daoFactory = new DAOFactory();
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());

        List<Patient> patientList = patientDAO.select();

        // Add patients in model
        model.addAttribute("patientList", patientList);

        return "adminViewPatients";
    }

    @GetMapping("/delete/{cnp}")
    public String deletePatient(@PathVariable long cnp, Model model) throws SQLException {
        System.out.println("Am intrat sa sterg persoana cu cnp-ul: " + cnp);

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());

        String email = patientDAO.getEmail(cnp);

        appointmentsDAO.deleteAllPatientAppointments(cnp);
        patientDAO.delete(cnp);
        userAuthenticationDAO.delete(email);

        return "redirect:/adminViewPatients"; // redirect to homepage
    }
}
