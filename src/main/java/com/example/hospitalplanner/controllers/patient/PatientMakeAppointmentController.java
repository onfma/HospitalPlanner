package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.database.DAO.AppointmentsDAO;
import com.example.hospitalplanner.database.DAO.CabinetsDAO;
import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.Cabinet;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/chooseSpecialty")
public class PatientMakeAppointmentController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showMakeAnAppointmentPage(Model model) throws SQLException {
        System.out.println("S-a afisat pagina makeAnAppointment.hmtl!");

        DAOFactory daoFactory = new DAOFactory();
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());

        List<Cabinet> cabinetList = cabinetsDAO.select();

        model.addAttribute("cabinets", cabinetList);

        return "patient/patientMakeAppointment"; // redirect to homepage
    }
}