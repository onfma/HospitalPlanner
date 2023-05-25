package com.example.hospitalplanner.controllers.admin;

import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.exceptions.ChangeAccountException;
import com.example.hospitalplanner.exceptions.ChangeAccountSuccess;
import com.example.hospitalplanner.models.AppointmentModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/adminModifyPatient")
public class AdminModifyPatientController {
    @Autowired
    private HttpSession session;

    @GetMapping("/{cnp}")
    public String showPatient(@PathVariable long cnp, Model model) throws SQLException {
        session.removeAttribute("cnp");
        session.setAttribute("cnp", cnp);

        System.out.println("S-a afisat pagina adminModifyPatient.hmtl pt pacientul: " + cnp);

        DAOFactory daoFactory = new DAOFactory();
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());

        Patient patient = new Patient();

        // Patient's information
        patient.setCnp(cnp);
        patient.setFirstName(patientDAO.getFirstName(cnp));
        patient.setLastName(patientDAO.getLastName(cnp));
        patient.setGender(patientDAO.getGender(cnp));
        patient.setPhoneNumber(patientDAO.getPhoneNumber(cnp));
        patient.setAddress(patientDAO.getAddress(cnp));
        patient.setEmail(patientDAO.getEmail(cnp));

        // Add patient's information in model
        model.addAttribute("patient", patient);

        // Patient's Appointments
        List<Appoinments> appointmentsList = appointmentsDAO.getPatientAppointments(cnp);

        List<AppointmentModel> appointments = new ArrayList<>();

        for (Appoinments appointment : appointmentsList) {
            int id = appointment.getId();
            String doctorFirstName = doctorDAO.getFirstName(appointment.getDoctorCNP());
            String doctorLastName = doctorDAO.getLastName(appointment.getDoctorCNP());
            String specialityName = cabinetsDAO.getSpecialityName(appointment.getCabinetID());
            LocalDateTime appointmentTime = appointment.getAppointmentTime();

            AppointmentModel appointmentModel = new AppointmentModel(id, doctorFirstName, doctorLastName, specialityName, appointmentTime);

            appointments.add(appointmentModel);
        }

        Collections.sort(appointments, (a1, a2) -> a2.getAppointmentTime().compareTo(a1.getAppointmentTime()));

        // Add patient's appointments in model
        model.addAttribute("appointments", appointments);

        return "adminModifyPatient"; // redirect to homepage
    }

    @GetMapping("/cancelAppointment/{appointmentId}")
    public String cancelAppointment(@PathVariable int appointmentId, Model model) throws SQLException {
        System.out.println("Am intrat sa sterg programarea cu id-ul: " + appointmentId);

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());

        appointmentsDAO.delete(appointmentId);

        long cnp = (long) session.getAttribute("cnp");
        String redirect = "redirect:/adminModifyPatient/" + cnp;

        return redirect; // redirect to patient dashboard
    }


    @PostMapping("/{modifyAccount}")
    public String processChangeAccountForm(@RequestParam("first_name") String newFirstName,
                                           @RequestParam("last_name") String newLastName,
                                           @RequestParam("tel") String newPhoneNumber,
                                           @RequestParam("email") String newEmail,
                                           @RequestParam("home_address") String newAddress,
                                           @RequestParam("pswd") String newPassword,
                                           Model model) throws SQLException, ChangeAccountException, ChangeAccountSuccess {

        DAOFactory daoFactory = new DAOFactory();

        // verify if it's patient or doctor
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        long cnp = (long) session.getAttribute("cnp");

        System.out.println("\nAm intrat sa editez pt " + cnp);

        if(patientDAO.existsByCNP(cnp) == true) { // it's a patient
            System.out.println("\nExista pacientul cu cnp-ul: " + cnp);

            String personEmail = patientDAO.getEmail(cnp);

            UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());

            if(!newFirstName.equals("")) // change FirstName
                patientDAO.setFirstName(cnp, newFirstName);

            if(!newLastName.equals("")) // change LastName
                patientDAO.setLastName(cnp, newLastName);

            if(!newPhoneNumber.equals(""))  // change phoneNumber
                patientDAO.setPhoneNumber(cnp, newPhoneNumber);

            if(!newEmail.equals("")) { // change Email
                if(!userAuthenticationDAO.exists(newEmail)) { // if email doesn't exist in DB
                    // insert a new row in "UserAuthentication" table with the new email
                    Patient patient = new Patient();

                    patient.setEmail(newEmail);
                    patient.setPasswordController(userAuthenticationDAO.getPassword(personEmail));
                    patient.setSalt(userAuthenticationDAO.getSalt(personEmail));

                    userAuthenticationDAO.insert(patient);

                    // update teh email in "Pacient" table
                    patientDAO.setEmail(cnp, newEmail);

                    // delete the old email from "UserAuthentication" table
                    userAuthenticationDAO.delete(personEmail);

                    // update email in "session" field
                    session.setAttribute("email", newEmail);
                }
            }

            if(!newAddress.equals("")) // change address
                patientDAO.setAddress(cnp, newAddress);

            if(!newPassword.equals("")) // change password
                userAuthenticationDAO.setPassword(personEmail, newPassword);
//            throw new ChangeAccountSuccess("Your account information has been successfully updated!");
        }

        String redirect = "redirect:/adminModifyPatient/" + cnp;

        return redirect; // redirect to patient dashboard
    }
}
