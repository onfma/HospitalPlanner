package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Examination;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.exceptions.DoctorChangeAccountException;
import com.example.hospitalplanner.exceptions.MakeAppointmentException;
import com.example.hospitalplanner.models.AppointmentModel;
import com.example.hospitalplanner.models.MakeAppointmetModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/makeAppointment")
public class PatientMakeAppointmentCabinetController {
    private String date;
    private String time;
    private int examinationId;
    private int cabinetID;
    private long doctorCnp;
    private String appointmentDayOfWeekString;
    private DoctorsSpecialitiesDAO doctorsSpecialitiesDAO;
    private DoctorsScheduleDAO doctorsScheduleDAO;
    private AppointmentsDAO appointmentsDAO;
    private ExaminationDAO examinationDAO;
    private LocalDateTime appointmentDateTime;
    private DoctorDAO doctorDAO;
    private CabinetsScheduleDAO cabinetsScheduleDAO;
    private long patientCNP;

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
                                             @RequestParam("time") String time,
                                             Model model) throws SQLException, MakeAppointmentException {

        this.doctorCnp = doctorCnp;
        this.date = date;
        this.time = time;
        this.examinationId = examinationId;

        DAOFactory daoFactory = new DAOFactory();
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        CabinetsScheduleDAO cabinetsScheduleDAO = new CabinetsScheduleDAO(daoFactory.getConnection());
        DoctorsScheduleDAO doctorsScheduleDAO = new DoctorsScheduleDAO(daoFactory.getConnection());
        ExaminationDAO examinationDAO = new ExaminationDAO(daoFactory.getConnection());
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());

        this.doctorsScheduleDAO = doctorsScheduleDAO;
        this.doctorsSpecialitiesDAO = doctorsSpecialitiesDAO;
        this.appointmentsDAO = appointmentsDAO;
        this.examinationDAO = examinationDAO;
        this.doctorDAO = doctorDAO;
        this.cabinetsScheduleDAO = cabinetsScheduleDAO;

        int cabinetID = (int) session.getAttribute("cabinetId");
        this.cabinetID = cabinetID;
        String personEmail = (String) session.getAttribute("email");
        long patientCNP = patientDAO.getCNP(personEmail);
        this.patientCNP = patientCNP;
        String redirect = "redirect:/makeAppointment/" + cabinetID;


        System.out.println("Programarea se face pt:" +
                "\n\t- email pacient: " + personEmail +
                "\n\t- cabinetID: " + cabinetID +
                "\n\t- doctorCNP: " + doctorCnp +
                "\n\t- examinationID: " + examinationId +
                "\n\t- date: " + date +
                "\n\t- time: " + time);

        LocalDateTime appointmentDateTime = LocalDateTime.parse(date + "T" + time);
        this.appointmentDateTime = appointmentDateTime;
        LocalDateTime currentDateTime = LocalDateTime.now();

        // if date and time are in the past
        if (appointmentDateTime.isBefore(currentDateTime)) {
            System.out.println("Error: The specified date and time are in the past!");
            return redirect;
        }

        // if the date is on weekend days
        DayOfWeek dayOfWeek = appointmentDateTime.getDayOfWeek();
        String appointmentDayOfWeekString = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
        this.appointmentDayOfWeekString = appointmentDayOfWeekString;
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            System.out.println("Error: You cannot make an appointment on weekends!");
            return redirect;
        }

        List<CabinetSchedule> cabinetScheduleList = cabinetsScheduleDAO.getCabinetSchedule_FullWeek(cabinetID);

        // verify if it's in cabinet schedule or not
        if(isInCabinetSchedule(appointmentDateTime, cabinetScheduleList)) {
            System.out.println("Ora programării nu este disponibilă în programul cabinetului.");
            return redirect;
        }

        // verify if appointment patient time is available in doctor schedule
        List<Appoinments> doctorAppointmentsPatientDay = appointmentsDAO.getDoctorAppointments(doctorCnp);

        int appointmentDuration = (int) examinationDAO.getAverageDuration(examinationId);

        if (appointmentDuration == 0)
            appointmentDuration = 30;

        if(isSlotAvailable(doctorAppointmentsPatientDay, appointmentDateTime, appointmentDuration)) {
            System.out.println("\nSe poate face programarea pt ora si doctorul cerute!");

            Appoinments newAppointment = new Appoinments();
            newAppointment.setId(appointmentsDAO.getMaxAppointmentId() + 1);
            newAppointment.setCabinetID(cabinetID);
            newAppointment.setDoctorCNP(doctorCnp);
            newAppointment.setPatientCNP(patientCNP);

            LocalDate localDate = LocalDate.parse(date);
            LocalTime localTime = LocalTime.parse(time);

            // Crearea obiectului LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

            newAppointment.setAppointmentTime(localDateTime);
            newAppointment.setExaminationID(examinationId);

            appointmentsDAO.insert(newAppointment);

            return "redirect:/myAppointments";
        } else { // if the slot isn't available
            System.out.println("\nNU se poate face programarea la doctorul si ora ceruta!");

            List<AppointmentModel> appoinmentSlots = new ArrayList<>();

            // recommend possible appointments for other doctor, but at the same time
            appoinmentSlots = recommendSameTimeDifferentDoctor(appoinmentSlots);

            // recommend possible appointments for the same doctor, but at different appointment time
            appoinmentSlots = recommendDifferentTimeSameDoctor(appoinmentSlots);

            PatientAppointmentRecommendationController PatientAppointmentRecommendationController = new PatientAppointmentRecommendationController();

            model.addAttribute("appoinmentSlots", appoinmentSlots);

            MakeAppointmetModel makeAppointmetModel = new MakeAppointmetModel();

            int cabinetId = (int) session.getAttribute("cabinetId");

            makeAppointmetModel.setCabinetID(cabinetId);
            makeAppointmetModel.setCabinetName(cabinetsDAO.getSpecialityName(cabinetId));
            makeAppointmetModel.setDoctorList(doctorsSpecialitiesDAO.getDoctorsHaveSameSpeciality(cabinetId));
            makeAppointmetModel.setExaminationList(examinationDAO.getCabinetExamination(cabinetId));
            makeAppointmetModel.setCabinetScheduleList(cabinetsScheduleDAO.getCabinetSchedule_FullWeek(cabinetId));

            Examination examination = new Examination();

            model.addAttribute("cabinet", makeAppointmetModel);

//            System.out.println("Posibile programari: " + appoinmentSlots);

//            PatientAppointmentRecommendationController.showPatientAppointmentRecommendationPage(model);
            return "patient/patientAppointmentRecommandation";
        }

// X        // cand se salveaza un raport -> modifica durata media in DB

// X        // daca data e in trecut : alert("Date is incorrect.");
// X        // daca este in weekend: alert("We don't work on the weekends.");
// X        // daca nu este in orarul cabinetului: alert("Not in the Cabinet Schedule. ");
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
//        return redirect; // redirect to patient dashboard
    }

    public boolean isInCabinetSchedule(LocalDateTime appointmentDateTime, List<CabinetSchedule> cabinetScheduleList){
        // Extract the day of the week from `appointment Date Time`
        DayOfWeek appointmentDayOfWeek = appointmentDateTime.getDayOfWeek();
        String appointmentDayOfWeekString = appointmentDayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());

        // Iterate through the list of CabinetSchedules to find the schedule corresponding to the day of the week
        for (CabinetSchedule schedule : cabinetScheduleList) {
            if (schedule.getDayOfWeek().equalsIgnoreCase(appointmentDayOfWeekString)) {
                LocalTime appointmentTime = appointmentDateTime.toLocalTime();
                if (appointmentTime.isAfter(schedule.getStartTime()) && appointmentTime.isBefore(schedule.getEndTime())) {
                    break;
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isSlotAvailable(List<Appoinments> doctorAppointmentsPatientDay, LocalDateTime appointmentDateTime, int duration) throws SQLException {
        DAOFactory daoFactory = new DAOFactory();
        ExaminationDAO examinationDAO = new ExaminationDAO(daoFactory.getConnection());

        LocalDateTime appointmentEndTime = appointmentDateTime.plusMinutes(duration);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        if(doctorAppointmentsPatientDay.isEmpty())
            return true;

        for(Appoinments appoinment : doctorAppointmentsPatientDay) {
             if(appoinment.getAppointmentTime().getDayOfWeek() == appointmentDateTime.getDayOfWeek()) { // if the appointment Doctor day is the same with the appointment Patient day
                int averageDoctorExamination = (int) examinationDAO.getAverageDuration(appoinment.getExaminationID());

                String patientStart = this.time;
                String patientEnd = appointmentEndTime.format(formatter);

                LocalDateTime appTime = appoinment.getAppointmentTime();
                int hour = appTime.getHour();
                int minute = appTime.getMinute();

                String doctorEnd = calculateAppointmentEndTime(appoinment, averageDoctorExamination);

                LocalTime patientStartTime = LocalTime.parse(patientStart);
                LocalTime patientEndTime = LocalTime.parse(patientEnd);
                LocalTime doctorStartTime = LocalTime.of(hour, minute);
                LocalTime doctorEndTime = LocalTime.parse(doctorEnd);

                System.out.println("\n\t-DocS: " + doctorStartTime +
                            "\n\t- docE: " + doctorEndTime +
                            "\n\t- patientS: " + patientStartTime +
                            "\n\t- patientE: " + patientEndTime);

                if(doctorStartTime.isBefore(patientStartTime) && patientStartTime.isBefore(doctorEndTime)) {
//                    System.out.println("\n\t-DocS: " + doctorStartTime +
//                            "\n\t- docE: " + doctorEndTime +
//                            "\n\t- patientS: " + patientStartTime +
//                            "\n\t- patientE: " + patientEndTime);
                    return false;
                }
                else if (patientStartTime.isBefore(doctorStartTime) && doctorStartTime.isBefore(patientEndTime)) {
//                    System.out.println("\n\t-DocS: " + doctorStartTime +
//                            "\n\t- docE: " + doctorEndTime +
//                            "\n\t- patientS: " + patientStartTime +
//                            "\n\t- patientE: " + patientEndTime);
                    return false;
                } else if (patientStartTime.equals(doctorStartTime)) {
                    return false;
                }
             }
        }

        return true;
    }

    public String calculateAppointmentEndTime(Appoinments appointment, int averageDuration) {
        LocalDateTime startTime = appointment.getAppointmentTime();

        if(averageDuration == 0)
            averageDuration = 30;
        LocalDateTime endTime = startTime.plusMinutes(averageDuration);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String endTimeString = endTime.format(formatter);

        return endTimeString;
    }

    public List<AppointmentModel> recommendDifferentTimeSameDoctor(List<AppointmentModel> appoinmentSlots) throws SQLException {
        List<Appoinments> doctorAppointmentsPatientDay = appointmentsDAO.getDoctorAppointments(doctorCnp);

        int appointmentDuration = (int) examinationDAO.getAverageDuration(examinationId);
        if (appointmentDuration == 0)
            appointmentDuration = 30;

        // create a map to store every (startTimeAppointment, endTimeAppointment) from a Doctor's Appointment Schedule
        Map<LocalTime, LocalTime> appointmentsMap = new HashMap<>();

        // Iterați prin lista de programări ale doctorului
        for (Appoinments appointment : doctorAppointmentsPatientDay) {
            LocalDateTime appointmentTime = appointment.getAppointmentTime();
            LocalTime startTime = appointmentTime.toLocalTime();
            LocalTime endTime = startTime.plusMinutes(appointmentDuration);

            // Adăugați startTime-ul și endTime-ul în Map
            appointmentsMap.put(startTime, endTime);
        }

        // sort in ascending order according to the start time of a doctor's appointment
        Map<LocalTime, LocalTime> sortedAppointmentsMap = new TreeMap<>(appointmentsMap);

        System.out.println("\nProgramarile doctorului:");
        // AFISAM programarile doctorului
        for (Map.Entry<LocalTime, LocalTime> entry : sortedAppointmentsMap.entrySet()) {
            LocalTime startTime = entry.getKey();
            LocalTime endTime = entry.getValue();

            System.out.println("\t- start: " + startTime + ", end: " + endTime);
        }

        // Create a new Map to store the endTime and the number of minutes available
        Map<LocalTime, Integer> availableTimeMap = new HashMap<>();

        LocalTime previousEndTime = null;

        LocalTime startDay = null;
        LocalTime finishDay = null;
        boolean findStartDay = false;

        // Iterate through the sorted map entries
        for (Map.Entry<LocalTime, LocalTime> entry : sortedAppointmentsMap.entrySet()) {
            LocalTime startTime = entry.getKey();
            LocalTime endTime = entry.getValue();

            if(!findStartDay) {
                startDay = startTime;
                findStartDay = true;
            }
            finishDay = endTime;

            // Calculate the number of minutes available between the endTime of the previous schedule and the startTime of the current schedule
            if (previousEndTime != null) {
                int minutesAvailable = (int) java.time.Duration.between(previousEndTime, startTime).toMinutes();
                availableTimeMap.put(previousEndTime, minutesAvailable);
            }

            previousEndTime = endTime;
        }

        System.out.println("\nstartDay: " + startDay + ", finishDay: " + finishDay);

        System.out.println("\ntimupul liber al doctorului:");

        boolean findAnyAppointment = false;
        // iteram pentru a gasi posibilele programari ale pacientului
        for (Map.Entry<LocalTime, Integer> entry : availableTimeMap.entrySet()) {
            LocalTime endTime = entry.getKey();
            int minutesAvailable = entry.getValue();

            System.out.println("\t- end: " + endTime + ", minutes available: " + minutesAvailable);

            if (minutesAvailable > appointmentDuration + 3) {
                findAnyAppointment = true;
                AppointmentModel addAppointment;
                addAppointment = createAppointment(endTime);
                appoinmentSlots.add(addAppointment);
            }
        }

        if(!findAnyAppointment) {
            // Extract the day of the week from `appointment Date Time`
            DayOfWeek appointmentDayOfWeek = appointmentDateTime.getDayOfWeek();
            String appointmentDayOfWeekString = appointmentDayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());

            DoctorSchedule doctorSchedule = doctorsScheduleDAO.getDoctorSchedule_SpecificDay(doctorCnp, appointmentDayOfWeekString);

            LocalTime doctorStartDay = doctorSchedule.getStartTime();
            LocalTime doctorEndDay = doctorSchedule.getEndTime();

            if(startDay.minusMinutes(appointmentDuration + 3).isAfter(doctorStartDay)) {
                AppointmentModel addAppointment;
                addAppointment = createAppointment(startDay.minusMinutes(appointmentDuration + 3));
                appoinmentSlots.add(addAppointment);
            }

            if(finishDay.plusMinutes(appointmentDuration + 3).isBefore(doctorEndDay)) {
                AppointmentModel addAppointment;
                addAppointment = createAppointment(finishDay.plusMinutes(appointmentDuration + 3));
                appoinmentSlots.add(addAppointment);
            }

        }


        return appoinmentSlots;
    }

    public List<AppointmentModel> recommendSameTimeDifferentDoctor(List<AppointmentModel> appoinmentSlots) throws SQLException {

        List<Doctor> doctorList = doctorsSpecialitiesDAO.getDoctorsHaveSameSpeciality(cabinetID);

        // delete the doctor from the patient form
        for(Doctor doctor : doctorList)
            if(doctor.getCnp() == doctorCnp)
                doctorList.remove(doctor);

        for(Doctor doctor : doctorList) {
            List<DoctorSchedule> doctorScheduleList = doctorsScheduleDAO.getDoctorSchedule_FullWeek(doctor.getCnp());

            DoctorSchedule doctorScheduleDay = doctorsScheduleDAO.getDoctorSchedule_SpecificDay(doctor.getCnp(), appointmentDayOfWeekString);

            List<Appoinments> doctorAppointmentsPatientDay = appointmentsDAO.getDoctorAppointments(doctor.getCnp());

            int appointmentDuration = (int) examinationDAO.getAverageDuration(examinationId);

            if (appointmentDuration == 0)
                appointmentDuration = 30;

            if(isSlotAvailable(doctorAppointmentsPatientDay, appointmentDateTime, appointmentDuration)) {

                AppointmentModel newAppointment = new AppointmentModel();
                newAppointment.setId(appointmentsDAO.getMaxAppointmentId() + 1);
                newAppointment.setCabinetId(cabinetID);
                newAppointment.setDoctorCNP(doctor.getCnp());
                newAppointment.setDoctorFirstName(doctorDAO.getFirstName(newAppointment.getDoctorCNP()));
                newAppointment.setDoctorLastName(doctorDAO.getLastName(newAppointment.getDoctorCNP()));
                newAppointment.setPatientCNP(patientCNP);

                LocalDate localDate = LocalDate.parse(date);
                newAppointment.setLocalDate(localDate);
                LocalTime localTime = LocalTime.parse(time);
                newAppointment.setLocalTime(localTime);

                // Crearea obiectului LocalDateTime
                LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

                newAppointment.setAppointmentTime(localDateTime);
                newAppointment.setExaminationID(examinationId);
                newAppointment.setExaminationName(examinationDAO.getExaminationName(newAppointment.getExaminationID()));

                appoinmentSlots.add(newAppointment);
            }
        }

        return appoinmentSlots;
    }

    public AppointmentModel createAppointment(LocalTime startTime) throws SQLException {
        AppointmentModel newAppointment = new AppointmentModel();
        newAppointment.setId(appointmentsDAO.getMaxAppointmentId() + 1);
        newAppointment.setCabinetId(cabinetID);
        newAppointment.setDoctorCNP(doctorCnp);
        newAppointment.setDoctorFirstName(doctorDAO.getFirstName(newAppointment.getDoctorCNP()));
        newAppointment.setDoctorLastName(doctorDAO.getLastName(newAppointment.getDoctorCNP()));
        newAppointment.setPatientCNP(patientCNP);

        LocalDate localDate = LocalDate.parse(date);
        newAppointment.setLocalDate(localDate);

        LocalTime newStartTime = startTime.plusMinutes(3);
        newAppointment.setLocalTime(newStartTime);

        // Crearea obiectului LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.of(localDate, newStartTime);

        newAppointment.setAppointmentTime(localDateTime);
        newAppointment.setExaminationID(examinationId);
        newAppointment.setExaminationName(examinationDAO.getExaminationName(newAppointment.getExaminationID()));

        return newAppointment;
    }
}