package com.example.hospitalplanner.controllers.doctor;

import com.example.hospitalplanner.database.DAO.AppointmentsDAO;
import com.example.hospitalplanner.database.DAO.CabinetsDAO;
import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.models.AppointmentModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/doctorMyAppointments")
public class DoctorViewAppointmentsController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showDoctorDashboard(Model model) throws SQLException {
        System.out.println("S-a afisat pagina doctorViewAppointments.html!");

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());

        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");

        long cnp = 0;
        if(doctorDAO.existsByEmail(personEmail) == true)  // it's a patient
            cnp = doctorDAO.getCNP(personEmail);

        List<Appoinments> appointments = appointmentsDAO.getPatientAppointments(cnp);

        List<AppointmentModel> doctorsAppointments  = new ArrayList<>();

        for (Appoinments appointment : appointments) {
            int id = appointment.getId();
            String doctorFirstName = doctorDAO.getFirstName(appointment.getDoctorCNP());
            String doctorLastName = doctorDAO.getLastName(appointment.getDoctorCNP());
            String specialityName = cabinetsDAO.getSpecialityName(appointment.getCabinetID());
            LocalDateTime appointmentTime = appointment.getAppointmentTime();

            AppointmentModel appointmentModel = new AppointmentModel(id, doctorFirstName, doctorLastName, specialityName, appointmentTime);
            doctorsAppointments.add(appointmentModel);
        }

        // Add the list of doctor's appointments to the model
        model.addAttribute("doctorsAppointments", doctorsAppointments);

        return "doctor/doctorViewAppointments";
    }
}