package com.example.hospitalplanner.controllers.admin;

import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.Examination;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import com.example.hospitalplanner.exceptions.AuthenticationException;
import com.example.hospitalplanner.models.MakeAppointmetModel;
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
@RequestMapping("/adminViewCabinets")
public class AdminViewCabinetsController {
    @Autowired
    private HttpSession session;

    @GetMapping
    public String showAdminDashboard(Model model) throws SQLException {
        session.removeAttribute("cabinet_id");

        System.out.println("S-a afisat pagina adminViewCabinets.hmtl!");

        DAOFactory daoFactory = new DAOFactory();
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());

        List<Cabinet> cabinetList = cabinetsDAO.select();

        // Add cabinets in model
        model.addAttribute("cabinetList", cabinetList);

        return "adminViewCabinets";
    }


    @GetMapping("/{cabinetId}")
    public String deleteCabinet(@PathVariable int cabinetId, Model model) throws SQLException {
        System.out.println("Am intrat sa sterg cabinetul " + cabinetId);

        DAOFactory daoFactory = new DAOFactory();
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());
        CabinetsScheduleDAO cabinetsScheduleDAO = new CabinetsScheduleDAO(daoFactory.getConnection());
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());
        ExaminationDAO examinationDAO = new ExaminationDAO(daoFactory.getConnection());

        appointmentsDAO.deleteAllCabinetAppointments(cabinetId);
        cabinetsScheduleDAO.deleteALLCabinetSchedule(cabinetId);
        doctorsSpecialitiesDAO.deleteAllCabinetsDoctor(cabinetId);
        examinationDAO.deleteAllCabinetExaminations(cabinetId);
        cabinetsDAO.delete(cabinetId);

        return "redirect:/adminViewCabinets"; // redirect to homepage
    }


    @PostMapping("/create")
    public String processCreateCabinetForm(@RequestParam("new_cabinet") String cabinetName) throws SQLException, AuthenticationException {

        DAOFactory daoFactory = new DAOFactory();
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());
        CabinetsScheduleDAO cabinetsScheduleDAO = new CabinetsScheduleDAO(daoFactory.getConnection());

        Cabinet cabinet = new Cabinet(cabinetsDAO.getMaxId() + 1, cabinetName);
        cabinetsDAO.insert(cabinet);

        CabinetSchedule monday = new CabinetSchedule(cabinet.getId(), "Monday", LocalTime.of(0,0),  LocalTime.of(0,0));
        CabinetSchedule tuesday = new CabinetSchedule(cabinet.getId(), "Tuesday", LocalTime.of(0,0),  LocalTime.of(0,0));
        CabinetSchedule wednesday = new CabinetSchedule(cabinet.getId(), "Wednesday", LocalTime.of(0,0),  LocalTime.of(0,0));
        CabinetSchedule thursday = new CabinetSchedule(cabinet.getId(), "Thursday", LocalTime.of(0,0),  LocalTime.of(0,0));
        CabinetSchedule friday = new CabinetSchedule(cabinet.getId(), "Friday", LocalTime.of(0,0),  LocalTime.of(0,0));

        cabinetsScheduleDAO.insert(monday);
        cabinetsScheduleDAO.insert(tuesday);
        cabinetsScheduleDAO.insert(wednesday);
        cabinetsScheduleDAO.insert(thursday);
        cabinetsScheduleDAO.insert(friday);

        return "redirect:/adminViewCabinets";
    }
}
