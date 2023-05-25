package com.example.hospitalplanner.controllers.admin;

import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.person.Patient;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.DoctorSpeciality;
import com.example.hospitalplanner.models.MakeAppointmetModel;
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
@RequestMapping("/adminModifyDoctor")
public class AdminModifyDoctorController {
    @Autowired
    private HttpSession session;

    @GetMapping("/{cnp}")
    public String showAdminDashboard(@PathVariable long cnp, Model model) throws SQLException {
        System.out.println("S-a afisat pagina adminModifyDoctor.hmtl!");

        session.removeAttribute("cnp");
        session.setAttribute("cnp", cnp);

        DAOFactory daoFactory = new DAOFactory();
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());


        Doctor doctor = new Doctor();

        // Patient's information
        doctor.setCnp(cnp);
        doctor.setFirstName(doctorDAO.getFirstName(cnp));
        doctor.setLastName(doctorDAO.getLastName(cnp));
        doctor.setGender(doctorDAO.getGender(cnp));
        doctor.setPhoneNumber(doctorDAO.getPhoneNumber(cnp));
        doctor.setAddress(doctorDAO.getAddress(cnp));
        doctor.setEmail(doctorDAO.getEmail(cnp));

        // Add patient's information in model
        model.addAttribute("doctor", doctor);

        // Doctor Specializations
        List<DoctorSpeciality> doctorSpecialityList = doctorsSpecialitiesDAO.getDoctorSpecialities(cnp);
        List<MakeAppointmetModel> doctorSpecialities = new ArrayList<>();

        for(DoctorSpeciality doctorSpeciality : doctorSpecialityList) {
            MakeAppointmetModel makeAppointmetModel = new MakeAppointmetModel();
            makeAppointmetModel.setCabinetID(doctorSpeciality.getCabinetID());
            makeAppointmetModel.setCabinetName(cabinetsDAO.getSpecialityName(doctorSpeciality.getCabinetID()));

            doctorSpecialities.add(makeAppointmetModel);
        }

        // Doctor NE-Specializations
        List<Cabinet> allCabinets = cabinetsDAO.select();

        List<MakeAppointmetModel> doctorNESpecialities = new ArrayList<>();

        for(Cabinet cabinet : allCabinets) {
            boolean exists = false;

            for (DoctorSpeciality doctorSpeciality : doctorSpecialityList) {
                if (cabinet.getId() == doctorSpeciality.getCabinetID()) {
                    exists = true;
                    break;
                }
            }

            if(!exists) {
                MakeAppointmetModel makeAppointmetModel = new MakeAppointmetModel();
                makeAppointmetModel.setCabinetID(cabinet.getId());
                makeAppointmetModel.setCabinetName(cabinetsDAO.getSpecialityName(cabinet.getId()));

                doctorNESpecialities.add(makeAppointmetModel);
            }
        }

        // Add doctor in model
        model.addAttribute("doctor", doctor);
        model.addAttribute("doctorSpecialities", doctorSpecialities);
        model.addAttribute("doctorNESpecialities", doctorNESpecialities);

        return "adminModifyDoctor";
    }

    @GetMapping("/addSpeciality/{cnp}/{cabinetID}")
    public String addSpeciality(@PathVariable long cnp, @PathVariable int cabinetID, Model model) throws SQLException {
        System.out.println("ADD SPECIALITY: cnp + " + cnp + ", cabinetID: " + cabinetID);

        DAOFactory daoFactory = new DAOFactory();;
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());

        DoctorSpeciality doctorSpeciality = new DoctorSpeciality(cabinetID, cnp);

        doctorsSpecialitiesDAO.insert(doctorSpeciality);

        String redirect = "redirect:/adminModifyDoctor/" + cnp;

        return redirect;
    }


    @GetMapping("/deleteSpeciality/{cnp}/{cabinetID}")
    public String deleteSpeciality(@PathVariable long cnp, @PathVariable int cabinetID, Model model) throws SQLException {
        System.out.println("DELETE SPECIALITY: cnp + " + cnp + ", cabinetID: " + cabinetID);

        DAOFactory daoFactory = new DAOFactory();
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());

        appointmentsDAO.deleteAllCabinetAppointments(cabinetID);
        doctorsSpecialitiesDAO.delete(cnp, cabinetID);

        String redirect = "redirect:/adminModifyDoctor/" + cnp;

        return redirect;
    }
}
