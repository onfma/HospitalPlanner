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
import java.util.List;

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

        String examinationName = examinationDAO.getExaminationName(appoinment.getExaminationID());

        // Add the appointment to the model
        model.addAttribute("appointment", appointmentModel);
        model.addAttribute("examinationName", examinationName);

        return "doctor/doctorMakeReport";
    }

    @PostMapping("/saveReport")
    public String saveReport(@RequestParam("duration") int duration,
                             @RequestParam("diagnostic_text") String diagnosticText,
                             @RequestParam("treatment_text") String treatmentText,
                             @RequestParam("appointmentId") int appointmentId,
                             Model model) throws SQLException {
//        System.out.println("Am primit informatiile:\n\t- durata: " + duration + "\n\t- diagnostic: " + diagnosticText + "\n\t- tratament: " + treatmentText);
//        System.out.println("ID programare: " + appointmentId);

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());

        appointmentsDAO.setDuration(appointmentId, duration);
        appointmentsDAO.setDiagnosis(appointmentId, diagnosticText);
        appointmentsDAO.setTreatment(appointmentId, treatmentText);

        Appoinments appointment = appointmentsDAO.findAppoinment(appointmentId);

        int examinationID = appointment.getExaminationID();

        List<Appoinments> appointmentsWithSameExamination = appointmentsDAO.getAppointmentsSameExamination(examinationID);

        float averageDuration = 0;
        int sum = 0;
        for (Appoinments appoinmentSameExamination : appointmentsWithSameExamination)
            sum = sum + appoinmentSameExamination.getDuration();

        averageDuration = sum / appointmentsWithSameExamination.size();

        ExaminationDAO examinationDAO = new ExaminationDAO(daoFactory.getConnection());

        examinationDAO.setAverageDuration(examinationID, averageDuration);


        return "redirect:/doctorMyAppointments";
    }


}
