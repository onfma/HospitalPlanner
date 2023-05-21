package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.database.DAO.AppointmentsDAO;
import com.example.hospitalplanner.database.DAO.CabinetsDAO;
import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.models.AppointmentModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.hospitalplanner.entities.Appoinments;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/myAppointments")
public class PatientViewAppointmentsController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showMyAppointmentsPage(Model model) throws SQLException {
        System.out.println("S-a afisat pagina patientAppointments.hmtl!");

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());

        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");

        long cnp = 0;
        if(patientDAO.existsByEmail(personEmail) == true)  // it's a patient
            cnp = patientDAO.getCNP(personEmail);
        else if (doctorDAO.existsByEmail(personEmail) == true)
            cnp = doctorDAO.getCNP(personEmail);

        // Interoghează baza de date pentru a obține programările pacienților
        List<Appoinments> appointments = appointmentsDAO.getPatientAppointments(cnp);

        List<AppointmentModel> appointmentModels = new ArrayList<>();

        for (Appoinments appointment : appointments) {
            String doctorFirstName = doctorDAO.getFirstName(appointment.getDoctorCNP());
            String doctorLastName = doctorDAO.getLastName(appointment.getDoctorCNP());
            String specialityName = cabinetsDAO.getSpecialityName(appointment.getCabinetID());
            LocalDateTime appointmentTime = appointment.getAppointmentTime();

            AppointmentModel appointmentModel = new AppointmentModel(doctorFirstName, doctorLastName, specialityName, appointmentTime);
            appointmentModels.add(appointmentModel);
        }

        // Adaugă lista de programări în model
        model.addAttribute("appointments", appointmentModels);

        return "patientViewAppointments";
    }

    @PostMapping("/myAppointments/cancelAppointment")
    public String cancelAppointment(@RequestParam("appointmentId") int appointmentId) throws SQLException {
        System.out.println("Am intrat sa anulez programarea cu id-ul: " + appointmentId);
        // Exemplu de implementare simplă
        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());
        appointmentsDAO.delete(appointmentId);

        return "patientViewAppointments";
    }

}