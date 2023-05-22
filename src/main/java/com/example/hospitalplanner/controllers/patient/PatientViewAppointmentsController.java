package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.database.DAO.AppointmentsDAO;
import com.example.hospitalplanner.database.DAO.CabinetsDAO;
import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.models.AppointmentModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.hospitalplanner.entities.Appoinments;
import org.springframework.ui.Model;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

        // Interoghează baza de date pentru a obține programările pacienților
        List<Appoinments> appointments = appointmentsDAO.getPatientAppointments(cnp);

        List<AppointmentModel> upcomingAppointments  = new ArrayList<>();
        List<AppointmentModel> pastAppointments   = new ArrayList<>();

        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Appoinments appointment : appointments) {
            int id = appointment.getId();
            String doctorFirstName = doctorDAO.getFirstName(appointment.getDoctorCNP());
            String doctorLastName = doctorDAO.getLastName(appointment.getDoctorCNP());
            String specialityName = cabinetsDAO.getSpecialityName(appointment.getCabinetID());
            LocalDateTime appointmentTime = appointment.getAppointmentTime();

            AppointmentModel appointmentModel = new AppointmentModel(id, doctorFirstName, doctorLastName, specialityName, appointmentTime);
            if (appointmentTime.isAfter(currentDateTime)) {
                upcomingAppointments.add(appointmentModel);
            } else {
                pastAppointments.add(appointmentModel);
            }
        }

        // Sort future appointments in ascending order by appointment date
        Collections.sort(upcomingAppointments, (a1, a2) -> a1.getAppointmentTime().compareTo(a2.getAppointmentTime()));

        // Sort previous appointments in descending order by appointment date
        Collections.sort(pastAppointments, (a1, a2) -> a2.getAppointmentTime().compareTo(a1.getAppointmentTime()));

        // Add the list of future and previous appointments to the model
        model.addAttribute("upcomingAppointments", upcomingAppointments);
        model.addAttribute("pastAppointments", pastAppointments);

        return "patient/patientViewAppointments";
    }

    @PostMapping("/cancelAppointment")
    public ResponseEntity<String> cancelAppointment(@RequestBody Map<String, String> request) throws SQLException {
        int appointmentId = Integer.parseInt(request.get("appointmentId"));

        System.out.println("Am intrat sa anulez programarea cu id-ul: " + appointmentId);

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());

        appointmentsDAO.delete(appointmentId);

        return ResponseEntity.ok("The appointment has been successfully deleted!");
    }

}