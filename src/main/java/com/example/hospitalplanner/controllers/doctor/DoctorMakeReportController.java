package com.example.hospitalplanner.controllers.doctor;

import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.person.Person;
import com.example.hospitalplanner.models.AppointmentModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/doctorMakeReport")
public class DoctorMakeReportController {
    @Autowired
    private HttpSession session;


    @GetMapping("/{appointmentId}")
    public String showDoctorDashboard(@PathVariable int appointmentId, Model model) throws SQLException {
        System.out.println("S-a afisat pagina doctorMakeReport.html");

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        ExaminationDAO examinationDAO = new ExaminationDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        Appoinments appoinment = appointmentsDAO.findAppoinment(appointmentId);

        AppointmentModel appointmentModel = new AppointmentModel();

        Patient patient = patientDAO.search(appoinment.getPatientCNP());

        appointmentModel.setPatientFirstName(patient.getFirstName());
        appointmentModel.setPatientLastName(patient.getLastName());
        appointmentModel.setPatientCNP(patient.getCnp());
        appointmentModel.setPatientGender(patient.getGender());

        // Add the appointment to the model
        model.addAttribute("appointment", appointmentModel);

        return "doctor/doctorMakeReport";
    }
}
