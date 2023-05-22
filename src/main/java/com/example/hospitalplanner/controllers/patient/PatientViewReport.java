package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.database.DAO.AppointmentsDAO;
import com.example.hospitalplanner.database.DAO.CabinetsDAO;
import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.ExaminationDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.models.AppointmentModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("/viewReport")
public class PatientViewReport {
    @Autowired
    private HttpSession session;

    @GetMapping("/{appointmentId}")
    public String generateReport(@PathVariable int appointmentId, Model model) throws SQLException {
        System.out.println("Am intrat sa afisez raportul cu id-ul: " + appointmentId);

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());
        ExaminationDAO examinationDAO = new ExaminationDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        Appoinments appoinment = appointmentsDAO.findAppoinment(appointmentId);

        AppointmentModel appointmentModel = new AppointmentModel();

        appointmentModel.setCabinetName(cabinetsDAO.getSpecialityName(appoinment.getCabinetID()));
        appointmentModel.setExaminationType(examinationDAO.getExaminationName(appoinment.getExaminationID()));
        appointmentModel.setDoctorFirstName(doctorDAO.getFirstName(appoinment.getDoctorCNP()));
        appointmentModel.setDoctorLastName(doctorDAO.getLastName(appoinment.getDoctorCNP()));
        appointmentModel.setAppointmentTime(appoinment.getAppointmentTime());
        appointmentModel.setDiagnosis(appoinment.getDiagnosis());
        appointmentModel.setTreatment(appoinment.getTreatment());

        // Add the appointment to the model
        model.addAttribute("appointment", appointmentModel);

        return "patient/patientViewReport";
    }
}