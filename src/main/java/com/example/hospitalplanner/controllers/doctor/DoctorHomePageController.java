package com.example.hospitalplanner.controllers.doctor;

import com.example.hospitalplanner.database.DAO.DoctorDAO;
import com.example.hospitalplanner.database.DAO.DoctorsScheduleDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/doctorDashboard")
public class DoctorHomePageController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showDoctorDashboard(Model model) throws SQLException {
        System.out.println("S-a afisat pagina doctorDashboard.html! E conectat: " + session.getAttribute("email"));

        // Doctor Information
        DAOFactory daoFactory = new DAOFactory();
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
        DoctorsScheduleDAO doctorsScheduleDAO = new DoctorsScheduleDAO(daoFactory.getConnection());

        String personEmail = (String) session.getAttribute("email");
        long cnp = doctorDAO.getCNP(personEmail);

        List<DoctorSchedule> doctorScheduleList = doctorsScheduleDAO.getDoctorSchedule_FullWeek(cnp);

        if(doctorScheduleList.size() > 0) {

            DoctorSchedule elementAtPosition2 = doctorScheduleList.remove(2);
            doctorScheduleList.add(elementAtPosition2);

            DoctorSchedule firstElement = doctorScheduleList.remove(0);
            doctorScheduleList.add(firstElement);
        }

        // Add doctor schedule in model
        model.addAttribute("name", doctorDAO.getFirstName(cnp) + " " + doctorDAO.getLastName(cnp));
        model.addAttribute("doctorScheduleList", doctorScheduleList);

        System.out.println(doctorScheduleList);

        return "doctor/doctorDashboard";
    }

    @PostMapping("/modifySchedule")
    public String modifyScheduleEndpoint(@RequestBody Map<String, Object> requestData, Model model) {
        try {
            DAOFactory daoFactory = new DAOFactory();
            DoctorsScheduleDAO doctorsScheduleDAO = new DoctorsScheduleDAO(daoFactory.getConnection());
            DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

            String personEmail = (String) session.getAttribute("email");
            long cnp = doctorDAO.getCNP(personEmail);

            // Obțineți listele JSON primite din corpul cererii
            List<String> modifiedHours = (List<String>) requestData.get("hours");
            List<String> modifiedDays = (List<String>) requestData.get("days");

            System.out.println("\nOrele modificate:\n\t" + modifiedHours);
            System.out.println("\nZilele modificate:\n\t" + modifiedDays);

            for (int i = 0; i < modifiedDays.size(); i++) {
                String startTime = modifiedHours.get(i*2);
                String endTime = modifiedHours.get(i*2+1);

                doctorsScheduleDAO.setStartTimeSpecificDay(cnp, modifiedDays.get(i), LocalTime.parse(startTime));
                doctorsScheduleDAO.setEndTimeSpecificDay(cnp, modifiedDays.get(i), LocalTime.parse(endTime));
            }

            List<DoctorSchedule> doctorScheduleList = doctorsScheduleDAO.getDoctorSchedule_FullWeek(cnp);

            System.out.println("\nProgramul final, dupa modificari:\n" + doctorScheduleList);

            return "redirect:/doctorDashboard";
        } catch (Exception e) {
            e.printStackTrace();
            return "errorPage"; // Pagina de eroare corespunzătoare
        }
    }
}