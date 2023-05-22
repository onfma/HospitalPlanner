package com.example.hospitalplanner.controllers.doctor;


import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.schedule.DoctorSpeciality;
import com.example.hospitalplanner.exceptions.ChangeAccountException;
import com.example.hospitalplanner.exceptions.ChangeAccountSuccess;
import com.example.hospitalplanner.models.MakeAppointmetModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/doctorViewAccount")
public class DoctorViewAccountController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showDoctorDashboard(Model model) throws SQLException {
        System.out.println("S-a afisat pagina doctorDashboard.html.hmtl!");

        // Doctor Information
        DAOFactory daoFactory = new DAOFactory();
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");
        long cnp = doctorDAO.getCNP(personEmail);

        Doctor doctor = new Doctor();

        doctor.setCnp(cnp);
        doctor.setFirstName(doctorDAO.getFirstName(cnp));
        doctor.setLastName(doctorDAO.getLastName(cnp));
        doctor.setGender(doctorDAO.getGender(cnp));
        doctor.setPhoneNumber(doctorDAO.getPhoneNumber(cnp));
        doctor.setAddress(doctorDAO.getAddress(cnp));
        doctor.setEmail(doctorDAO.getEmail(cnp));

        // Doctor Specializations
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());

        List<DoctorSpeciality> doctorSpecialityList = doctorsSpecialitiesDAO.getDoctorSpecialities(cnp);
        List<MakeAppointmetModel> doctorSpecialities = new ArrayList<>();

        for(DoctorSpeciality doctorSpeciality : doctorSpecialityList) {
            MakeAppointmetModel makeAppointmetModel = new MakeAppointmetModel();
            makeAppointmetModel.setCabinetName(cabinetsDAO.getSpecialityName(doctorSpeciality.getCabinetID()));

            doctorSpecialities.add(makeAppointmetModel);
        }

        // Doctor NE-Specializations
        List<DoctorSpeciality> doctorNESpecialityLisy = doctorsSpecialitiesDAO.select();
        List<MakeAppointmetModel> doctorNESpecialities = new ArrayList<>();

        for(DoctorSpeciality doctorSpeciality : doctorNESpecialityLisy) {
            MakeAppointmetModel makeAppointmetModel = new MakeAppointmetModel();
            makeAppointmetModel.setCabinetID(doctorSpeciality.getCabinetID());
            makeAppointmetModel.setCabinetName(cabinetsDAO.getSpecialityName(doctorSpeciality.getCabinetID()));

            doctorNESpecialities.add(makeAppointmetModel);
        }

        // eliminate specializations that are not of the doctor
        doctorNESpecialities.removeIf(speciality -> doctorSpecialities.stream().anyMatch(s -> s.getCabinetName().equals(speciality.getCabinetName())));
        doctorNESpecialities.remove(doctorNESpecialities.size() - 1);


        // Add doctor in model
        model.addAttribute("doctor", doctor);
        model.addAttribute("doctorSpecialities", doctorSpecialities);
        model.addAttribute("doctorNESpecialities", doctorNESpecialities);

        return "doctor/doctorViewAccount";
    }

    @PostMapping
    public String processChangeAccountForm(@RequestParam("first_name") String newFirstName,
                                           @RequestParam("last_name") String newLastName,
                                           @RequestParam("tel") String newPhoneNumber,
                                           @RequestParam("email") String newEmail,
                                           @RequestParam("home_address") String newAddress,
                                           @RequestParam("pswd") String newPassword,
                                           Model model) throws SQLException {

        System.out.println("Am intrat sa editez contul doctorului!");

        DAOFactory daoFactory = new DAOFactory();

        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");

        if (doctorDAO.existsByEmail(personEmail) == true) { // it's a doctor
            long cnp = doctorDAO.getCNP(personEmail);

            UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());
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
        }
        System.out.println("Am iesit sa editez contul doctorului!");
//        return "doctorViewAccount";
        return showDoctorDashboard(model); // redirect to doctor dashboard
    }

    @GetMapping("/addSpeciality/{cabinetID}")
    public String addSpeciality(@PathVariable int cabinetID, Model model) throws SQLException {
        System.out.println("ADD SPECIALITY");

        DAOFactory daoFactory = new DAOFactory();;
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");

        long cnp = 0;
        if (doctorDAO.existsByEmail(personEmail)) // it's a doctor
            cnp = doctorDAO.getCNP(personEmail);

        DoctorSpeciality doctorSpeciality = new DoctorSpeciality(cabinetID, cnp);

        doctorsSpecialitiesDAO.insert(doctorSpeciality);

        return "doctor/doctorViewAccount";
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
