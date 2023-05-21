package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.PatientDAO;
import com.example.hospitalplanner.database.DAO.UserAuthenticationDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.exceptions.ChangeAccountException;
import com.example.hospitalplanner.exceptions.ChangeAccountSuccess;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;


@Controller
@RequestMapping("/patientViewAccount")
public class PatientViewAccountController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showViewPatientAccountPage() {
        System.out.println("S-a afisat pagina patientViewAccount.hmtl!");
        return "patientViewAccount";
    }

    @PostMapping
    public String processChangeAccountForm(@RequestParam("first_name") String newFirstName,
                                           @RequestParam("last_name") String newLastName,
                                           @RequestParam("tel") String newPhoneNumber,
                                           @RequestParam("email") String newEmail,
                                           @RequestParam("home_address") String newAddress,
                                           @RequestParam("pswd") String newPassword) throws SQLException, ChangeAccountException, ChangeAccountSuccess {

        DAOFactory daoFactory = new DAOFactory();

        // verify if it's patient or doctor
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");

        if(patientDAO.existsByEmail(personEmail) == true) { // it's a patient
            long cnp = patientDAO.getCNP(personEmail);

            if(!newFirstName.equals("")) // verify if it's the same FirstName as in DB
                if(newFirstName.equals(patientDAO.getFirstName(cnp)))
                    throw new ChangeAccountException("You entered the same 'First Name' field!\nPlease fill in again!");

            if(!newLastName.equals(""))  // verify if it's the same LastName as in DB
                if(newLastName.equals(patientDAO.getLastName(cnp)))
                    throw new ChangeAccountException("You entered the same 'Last Name' field!\nPlease fill in again!");

            if(!newPhoneNumber.equals("")) // verify if it's the same LastName as in DB
                if(newPhoneNumber.equals(patientDAO.getPhoneNumber(cnp)))
                    throw new ChangeAccountException("You entered the same 'Phone Number' field!\nPlease fill in again!");

            if(!newEmail.equals("")) // verify if it's the same Email as in DB
                if(newEmail.equals(patientDAO.getEmail(cnp)))
                    throw new ChangeAccountException("You entered the same 'Email' field!\nPlease fill in again!");

            if(!newAddress.equals(""))  // verify if it's the same address as in DB
                if(newAddress.equals(patientDAO.getAddress(cnp)))
                    throw new ChangeAccountException("You entered the same 'Address' field!\nPlease fill in again!");

            UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());
            if(!newPassword.equals("")) { // verify if it's the same password as in DB
                String hashedPassword = hashPassword(newPassword, userAuthenticationDAO.getSalt(personEmail));

                if (hashedPassword.equals(userAuthenticationDAO.getPassword(personEmail)))
                    throw new ChangeAccountException("You entered the same 'Password' field!\nPlease fill in again!");
            }

            if(!newFirstName.equals("")) // change FirstName
                patientDAO.setFirstName(cnp, newFirstName);

            if(!newLastName.equals("")) // change LastName
                patientDAO.setLastName(cnp, newLastName);

            if(!newPhoneNumber.equals(""))  // change phoneNumber
                patientDAO.setPhoneNumber(cnp, newPhoneNumber);

            if(!newEmail.equals("")) { // change Email
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

            if(!newAddress.equals("")) // change address
                patientDAO.setAddress(cnp, newAddress);

            if(!newPassword.equals("")) // change password
                userAuthenticationDAO.setPassword(personEmail, newPassword);

            throw new ChangeAccountSuccess("Your account information has been successfully updated!");

        } else if (doctorDAO.existsByEmail(personEmail) == true) { // it's a doctor
            long cnp = doctorDAO.getCNP(personEmail);

            if(!newFirstName.equals("")) // verify if it's the same FirstName as in DB
                if(newFirstName.equals(doctorDAO.getFirstName(cnp)))
                    throw new ChangeAccountException("You entered the same 'First Name' field!\nPlease fill in again!");

            if(!newLastName.equals(""))  // verify if it's the same LastName as in DB
                if(newLastName.equals(doctorDAO.getLastName(cnp)))
                    throw new ChangeAccountException("You entered the same 'Last Name' field!\nPlease fill in again!");

            if(!newPhoneNumber.equals("")) // change phoneNumber // verify if it's the same LastName as in DB
                if(newPhoneNumber.equals(doctorDAO.getPhoneNumber(cnp)))
                    throw new ChangeAccountException("You entered the same 'Phone Number' field!\nPlease fill in again!");

            if(!newEmail.equals("")) // verify if it's the same Email as in DB
                if(newEmail.equals(doctorDAO.getEmail(cnp)))
                    throw new ChangeAccountException("You entered the same 'Email' field!\nPlease fill in again!");

            if(!newAddress.equals(""))  // verify if it's the same address as in DB
                if(newAddress.equals(doctorDAO.getAddress(cnp)))
                    throw new ChangeAccountException("You entered the same 'Address' field!\nPlease fill in again!");

            UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());
            if(!newPassword.equals("")) { // verify if it's the same password as in DB
                String hashedPassword = hashPassword(newPassword, userAuthenticationDAO.getSalt(personEmail));

                if (hashedPassword.equals(userAuthenticationDAO.getPassword(personEmail)))
                    throw new ChangeAccountException("You entered the same 'Password' field!\nPlease fill in again!");
            }

            /// change fields

            if(!newFirstName.equals("")) // change FirstName
                doctorDAO.setFirstName(cnp, newFirstName);

            if(!newLastName.equals("")) // change LastName
                doctorDAO.setLastName(cnp, newLastName);

            if(!newPhoneNumber.equals(""))  // change phoneNumber
                doctorDAO.setPhoneNumber(cnp, newPhoneNumber);

            if(!newEmail.equals("")) { // change Email
                // insert a new row in "UserAuthentication" table with the new email
                Doctor doctor = new Doctor();

                doctor.setEmail(newEmail);
                doctor.setPasswordController(userAuthenticationDAO.getPassword(personEmail));
                doctor.setSalt(userAuthenticationDAO.getSalt(personEmail));

                userAuthenticationDAO.insert(doctor);

                // update teh email in "Doctor" table
                doctorDAO.setEmail(cnp, newEmail);

                // delete the old email from "UserAuthentication" table
                userAuthenticationDAO.delete(personEmail);

                // update email in "session" field
                session.setAttribute("email", newEmail);
            }

            if(!newAddress.equals("")) // change address
                doctorDAO.setAddress(cnp, newAddress);

            if(!newPassword.equals("")) // change password
                userAuthenticationDAO.setPassword(personEmail, newPassword);

            throw new ChangeAccountSuccess("Your account information has been successfully updated!");
        }


        return "redirect:/patientViewAccount"; // redirect to homepage

//        return null;
    }

    private String hashPassword(String password, String salt) {
        // Hash password with salt
        String passwordAndSalt = password + salt;
        String hashedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPasswordBytes = md.digest(passwordAndSalt.getBytes());
            hashedPassword = Base64.getEncoder().encodeToString(hashedPasswordBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }
}