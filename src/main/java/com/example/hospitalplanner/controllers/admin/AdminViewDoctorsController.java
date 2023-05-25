package com.example.hospitalplanner.controllers.admin;

import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.schedule.DoctorSpeciality;
import com.example.hospitalplanner.models.DoctorModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/adminViewDoctors")
public class AdminViewDoctorsController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showAdminDashboard(Model model) throws SQLException {
        System.out.println("S-a afisat pagina adminViewDoctors.hmtl!");

        DAOFactory daoFactory = new DAOFactory();
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());

        List<Doctor> doctors = doctorDAO.select();

        List<DoctorModel> doctorList = new ArrayList<>();

        for(Doctor doctor : doctors) {
            DoctorModel doctorModel = new DoctorModel();

            doctorModel.setCnp(doctor.getCnp());
            doctorModel.setFirstName(doctor.getFirstName());
            doctorModel.setLastName(doctor.getLastName());

            List<DoctorSpeciality> doctorSpecialityList = doctorsSpecialitiesDAO.getDoctorSpecialities(doctor.getCnp());
            List<String> specialityListForModel = new ArrayList<>();

            for(DoctorSpeciality doctorSpeciality : doctorSpecialityList)
                specialityListForModel.add(cabinetsDAO.getSpecialityName(doctorSpeciality.getCabinetID()));

            doctorModel.setSpecialityList(specialityListForModel);

            doctorList.add(doctorModel);
        }

        // Add patients in model
        model.addAttribute("doctorList", doctorList);

        return "adminViewDoctors";
    }

    @GetMapping("/delete/{cnp}")
    public String deleteDoctor(@PathVariable long cnp, Model model) throws SQLException {
        System.out.println("Sterg doctorul cu cnp-ul: " + cnp);

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());
        DoctorsSpecialitiesDAO doctorSpecialityDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());
        DoctorsScheduleDAO doctorsScheduleDAO = new DoctorsScheduleDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
        UserAuthenticationDAO userAuthenticationDAO = new UserAuthenticationDAO(daoFactory.getConnection());

        String email = doctorDAO.getEmail(cnp);

        appointmentsDAO.deleteAllDoctorAppointments(cnp);
        doctorSpecialityDAO.deleteAllDoctorSpecialities(cnp);
        doctorsScheduleDAO.deleteALLDoctorDaySchedule(cnp);
        doctorDAO.delete(cnp);
        userAuthenticationDAO.delete(email);


        return "redirect:/adminViewDoctors";
    }

}
