package com.example.hospitalplanner.controllers.doctor;

import com.example.hospitalplanner.database.DAO.AppointmentsDAO;
import com.example.hospitalplanner.database.DAO.CabinetsDAO;
import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.models.AppointmentModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        if(doctorDAO.existsByEmail(personEmail) == true)
            cnp = doctorDAO.getCNP(personEmail);

        List<Appoinments> appointments = appointmentsDAO.getDoctorAppointments(cnp);

        List<AppointmentModel> doctorsAppointments  = new ArrayList<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Appoinments appointment : appointments) {
            AppointmentModel appointmentModel = new AppointmentModel();

            Patient patient = patientDAO.search(appointment.getPatientCNP());

            appointmentModel.setId(appointment.getId());
            appointmentModel.setPatientFirstName(patient.getFirstName());
            appointmentModel.setPatientLastName(patient.getLastName());
            System.out.println("CNP: " + appointment.getPatientCNP() + ", Nume pacient: " + appointmentModel.getPatientFirstName() + " " + appointmentModel.getPatientLastName());
            appointmentModel.setCabinetName(cabinetsDAO.getSpecialityName(appointment.getCabinetID()));
            appointmentModel.setAppointmentTime(appointment.getAppointmentTime());
            appointmentModel.setFormattedDate(appointment.getAppointmentTime().format(dateFormatter));
            appointmentModel.setFormattedTime(appointment.getAppointmentTime().format(timeFormatter));

            doctorsAppointments.add(appointmentModel);
        }

        // Add the list of doctor's appointments to the model
        model.addAttribute("doctorsAppointments", doctorsAppointments);

        return "doctor/doctorViewAppointments";
    }
}