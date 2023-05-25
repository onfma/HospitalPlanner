package com.example.hospitalplanner.controllers.admin;

import com.example.hospitalplanner.database.DAO.CabinetsDAO;
import com.example.hospitalplanner.database.DAO.CabinetsScheduleDAO;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/adminViewCabinetsSchedule")
public class AdminViewCabinetsScheduleController {
    @Autowired
    private HttpSession session;

    @GetMapping("/{cabinetId}")
    public String showViewCabinetPage(@PathVariable int cabinetId, Model model) throws SQLException {

        DAOFactory daoFactory = new DAOFactory();
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());
        CabinetsScheduleDAO cabinetsScheduleDAO = new CabinetsScheduleDAO(daoFactory.getConnection());

        List<CabinetSchedule> cabinetScheduleList = cabinetsScheduleDAO.getCabinetSchedule_FullWeek(cabinetId);

        String cabinetName = cabinetsDAO.getSpecialityName(cabinetId);

        model.addAttribute("name", cabinetName);
        model.addAttribute("cabinetScheduleList", cabinetScheduleList);

        session.setAttribute("cabinet_id", cabinetId);

        return "adminViewCabinetsSchedule"; // redirect to homepage
    }


    @PostMapping("/modifySchedule")
    public String modifyScheduleEndpoint(@RequestBody Map<String, Object> requestData, Model model) {
        try {
            DAOFactory daoFactory = new DAOFactory();
            CabinetsScheduleDAO cabinetsScheduleDAO = new CabinetsScheduleDAO(daoFactory.getConnection());

            // Obțineți listele JSON primite din corpul cererii
            List<String> modifiedHours = (List<String>) requestData.get("hours");
            List<String> modifiedDays = (List<String>) requestData.get("days");

            System.out.println("\nOrele modificate:\n\t" + modifiedHours);
            System.out.println("\nZilele modificate:\n\t" + modifiedDays);

            int cabinetID = (int) session.getAttribute("cabinet_id");
            System.out.println("\nCabinet id: " +cabinetID);

            for (int i = 0; i < modifiedDays.size(); i++) {
                String startTime = modifiedHours.get(i*2);
                String endTime = modifiedHours.get(i*2+1);

                cabinetsScheduleDAO.setStartTimeSpecificDay(cabinetID, modifiedDays.get(i), LocalTime.parse(startTime));
                cabinetsScheduleDAO.setEndTimeSpecificDay(cabinetID, modifiedDays.get(i), LocalTime.parse(endTime));
            }

//            List<CabinetSchedule> cabinetScheduleList = cabinetsScheduleDAO.getCabinetSchedule_FullWeek(cabinetID);

            return "redirect:/adminViewCabinets";
        } catch (Exception e) {
            e.printStackTrace();
            return "errorPage"; // Pagina de eroare corespunzătoare
        }
    }
}
