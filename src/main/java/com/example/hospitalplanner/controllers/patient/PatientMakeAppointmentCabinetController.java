package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import com.example.hospitalplanner.models.MakeAppointmetModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/makeAppointment")
public class PatientMakeAppointmentCabinetController {
    @Autowired
    private HttpSession session;

    @GetMapping("/{cabinetId}")
    public String showMakeAnAppointmentPage(@PathVariable int cabinetId, Model model) throws SQLException {
//        System.out.println("Am intrat pagina unde faci programare pt cabinetul cu id-ul: " + cabinetId);

        DAOFactory daoFactory = new DAOFactory();
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());
        ExaminationDAO examinationDAO = new ExaminationDAO(daoFactory.getConnection());
        CabinetsScheduleDAO cabinetsScheduleDAO = new CabinetsScheduleDAO(daoFactory.getConnection());

        MakeAppointmetModel makeAppointmetModel = new MakeAppointmetModel();

        //aici n.ar treb si in model sa vina si cobinetID? cred ca de asta nu imi da mie
        makeAppointmetModel.setCabinetID(cabinetId);
        makeAppointmetModel.setCabinetName(cabinetsDAO.getSpecialityName(cabinetId));
        makeAppointmetModel.setDoctorList(doctorsSpecialitiesDAO.getDoctorsHaveSameSpeciality(cabinetId));
        makeAppointmetModel.setExaminationList(examinationDAO.getCabinetExamination(cabinetId));
        makeAppointmetModel.setCabinetScheduleList(cabinetsScheduleDAO.getCabinetSchedule_FullWeek(cabinetId));

        model.addAttribute("cabinet", makeAppointmetModel);

        return "patient/patientMakeAppointmentCabinet"; // redirect to homepage
    }

    @GetMapping("/{cabinetId}/doctorSchedule/{cnp}")
    public ResponseEntity<List<DoctorSchedule>> getDoctorSchedule(@PathVariable long cnp) {
        try {
            System.out.println("Am intrat cu cabinet cnp-ul " + cnp);

            DAOFactory daoFactory = new DAOFactory();
            DoctorsScheduleDAO doctorsScheduleDAO = new DoctorsScheduleDAO(daoFactory.getConnection());

            List<DoctorSchedule> schedule = doctorsScheduleDAO.getDoctorSchedule_FullWeek(cnp);

            for(DoctorSchedule doctorSchedule : schedule)
                System.out.println(doctorSchedule);
            return ResponseEntity.ok(schedule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}