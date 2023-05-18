package com.example.hospitalplanner.controllers;

import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAO.UserAuthenticationDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.person.Person;
import com.example.hospitalplanner.exceptions.AuthenticationException;
import com.example.hospitalplanner.exceptions.CreateAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
@RequestMapping("/createAccount")
public class CreateAccountController {

    @GetMapping
    public String showCreateAccountPage() {
        System.out.println("S-a afisat pagina createAccountPage.hmtl!");
        return "createAccountPage";
    }

    @PostMapping
    public String processCreateAccountForm(@RequestParam("first_name") String firstName,
                                   @RequestParam("last_name") String lastName,
                                   @RequestParam("cnp") long cnp,
                                   @RequestParam("sex") char gender,
                                   @RequestParam("tel") String phoneNumber,
                                   @RequestParam("email") String email,
                                   @RequestParam("home_address") String adress,
                                   @RequestParam("pswd") String password,
                                   @RequestParam("account_type") String accountType) throws SQLException, CreateAccountException {

        DAOFactory daoFactory = new DAOFactory();

        // verify if already exists the email in the DB
        UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());

        if(!userAuthenticationDAO.exists(email)) {
            // insert email & password in "UserAuthentication" table
            if (accountType.equals("Patient")) { // it's a Patient
                Patient patient = new Patient(email, password);

                userAuthenticationDAO.insert(patient);

                // insert the rest of information in the "Patient" table
                PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
                Patient newPatient = new Patient(cnp, firstName, lastName, gender, phoneNumber, email, adress);

                patientDAO.insert(newPatient);

                return "redirect:/"; // redirect to homepage
            } else if (accountType.equals("Doctor")) { // it's a Doctor
                Doctor doctor = new Doctor(email, password);

                userAuthenticationDAO.insert(doctor);

                // insert the rest of information in the "Doctor" table
                DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
                Doctor newDoctor = new Doctor(cnp, firstName, lastName, gender, phoneNumber, email, adress);

                doctorDAO.insert(newDoctor);

                return null; //  redirect to page to complete speciality information, etc
            }
        } else
            throw new CreateAccountException("There is already an account with this email!\nPlease try again with another email adress!");
        return null;
    }
}
