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
import org.springframework.web.bind.annotation.*;

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

        Appoinments appoinment = appointmentsDAO.findAppoinment(appointmentId);

        AppointmentModel appointmentModel = new AppointmentModel();

        Patient patient = patientDAO.search(appoinment.getPatientCNP());

        appointmentModel.setPatientFirstName(patient.getFirstName());
        appointmentModel.setPatientLastName(patient.getLastName());
        appointmentModel.setPatientCNP(patient.getCnp());
        appointmentModel.setPatientGender(patient.getGender());
        appointmentModel.setDuration(appoinment.getDuration());
        appointmentModel.setDiagnosis(appoinment.getDiagnosis());
        appointmentModel.setTreatment(appoinment.getTreatment());

        System.out.println("diagnostic: " + appointmentModel.getDiagnosis());

        // Add the appointment to the model
        model.addAttribute("appointment", appointmentModel);

        return "doctor/doctorMakeReport";
    }

    @PostMapping("/saveReport")
    public String saveReport(@RequestParam("duration") int duration,
                             @RequestParam("diagnostic_text") String diagnosticText,
                             @RequestParam("treatment_text") String treatmentText,
                             @RequestParam("appointmentId") int appointmentId,
                             Model model) throws SQLException {
        System.out.println("Am primit informatiile:\n\t- durata: " + duration + "\n\t- diagnostic: " + diagnosticText + "\n\t- tratament: " + treatmentText);
        System.out.println("ID programare: " + appointmentId);

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());

        appointmentsDAO.setDuration(appointmentId, duration);
        appointmentsDAO.setDiagnosis(appointmentId, diagnosticText);
        appointmentsDAO.setTreatment(appointmentId, treatmentText);

        // Restul logicii de salvare a raportului

        return "redirect:/doctorMyAppointments";
    }


}
