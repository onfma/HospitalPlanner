package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.database.DAO.CabinetsDAO;
import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.DoctorsSpecialitiesDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.schedule.DoctorSpeciality;
import com.example.hospitalplanner.models.AppointmentModel;
import com.example.hospitalplanner.models.DoctorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/patientDashboard")
public class PatientHomePageController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showPatientDashboardPage(Model model) throws SQLException {
        System.out.println("S-a afisat pagina patientDashboard.hmtl! Este conectat clientul cu email-ul:" + session.getAttribute("email"));

        DAOFactory daoFactory = new DAOFactory();
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());

        List<Doctor> doctorList = doctorDAO.select();
        List<DoctorModel> doctorModels = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Doctor doctor = doctorList.get(i);
            String doctorFirstName = doctorDAO.getFirstName(doctor.getCnp());
            String doctorLastName = doctorDAO.getLastName(doctor.getCnp());
            List<DoctorSpeciality> specialties = doctorsSpecialitiesDAO.getDoctorSpecialities(doctor.getCnp());

            int cabinetID = specialties.get(0).getCabinetID();
            String specialityName = cabinetsDAO.getSpecialityName(cabinetID);

            DoctorModel doctorModel = new DoctorModel(doctorFirstName, doctorLastName, specialityName);
            doctorModels.add(doctorModel);
        }

        // add doctors list in model
        model.addAttribute("doctor", doctorModels);

        return "patient/patientDashboard";
    }
}
