package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import com.example.hospitalplanner.exceptions.ChangeAccountException;
import com.example.hospitalplanner.exceptions.ChangeAccountSuccess;
import com.example.hospitalplanner.exceptions.MakeAppointmentException;
import com.example.hospitalplanner.models.MakeAppointmetModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/makeAppointment")
public class PatientMakeAppointmentCabinetController {
    @Autowired
    private HttpSession session;

    @GetMapping("/{cabinetId}")
    public String showMakeAnAppointmentPage(@PathVariable int cabinetId, Model model) throws SQLException {
        session.removeAttribute("cabinetId");
        session.setAttribute("cabinetId", cabinetId);

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


    @PostMapping("/patientAppointment")
    public String processMakeAppointmentForm(@RequestParam("doctorCnp") long doctorCnp,
                                             @RequestParam("examinationId") int examinationId,
                                             @RequestParam("date") String date,
                                             @RequestParam("time") String time) throws SQLException, MakeAppointmentException {

        int cabinetID = (int) session.getAttribute("cabinetId");

        String personEmail = (String) session.getAttribute("email");

        System.out.println("Programarea se face pt:" +
                "\n\t- email pacient: " + personEmail +
                "\n\t- cabinetID: " + cabinetID +
                "\n\t- doctorCNP: " + doctorCnp +
                "\n\t- examinationID: " + examinationId +
                "\n\t- date: " + date +
                "\n\t- time: " + time);

        String redirect = "redirect:/makeAppointment/" + cabinetID;

        // cand se salveaza un raport -> modifica durata media in DB

        // daca data e in trecut : alert("Date is incorrect.");
        // daca este in weekend: alert("We don't work on the weekends.");
        // daca nu este in orarul cabinetului: alert("Not in the Cabinet Schedule. ");
        // daca este in afara orelor de munca ale doctorului: incearca sa gasesti alt doctor care este liber
            // daca nu e niciun doctor liber din cabinet -> eroare
        // daca nu are o programare atunci:
            // daca nu incepe o programare fix atunci
            // iterare prin toate programarile doctorului
                 // apelare funtie jos
            // creare functie afisare interval ora_start - ora_finala de programare
                    // daca liber -> programare creata!
            // daca nu e liber:
                // daca e liber in acea zi la o alta ora
                // cauta cel mai apropiat timp liber al doctorului:
                    // cauta cea mai apropiata programare (de ora) fata de cea a pacientului

                // daca nu e liber doctorul in ziua respectiva, cauta in programul celorlalti doctori din acelasi cabinet

//        throw new MakeAppointmentException("Ceva exceptie", redirect);

        return redirect; // redirect to patient dashboard
    }
}