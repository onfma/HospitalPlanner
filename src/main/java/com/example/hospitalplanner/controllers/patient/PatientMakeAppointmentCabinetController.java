package com.example.hospitalplanner.controllers.patient;

import com.example.hospitalplanner.database.DAO.*;
import com.example.hospitalplanner.database.DAOFactory;
import com.example.hospitalplanner.entities.Examination;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.DoctorSchedule;
import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.exceptions.CreateAccountException;
import com.example.hospitalplanner.exceptions.DoctorChangeAccountException;
import com.example.hospitalplanner.exceptions.MakeAppointmentException;
import com.example.hospitalplanner.models.AppointmentModel;
import com.example.hospitalplanner.models.MakeAppointmetModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/makeAppointment")
public class PatientMakeAppointmentCabinetController {
    private int idApp = 0;
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
    private AppointmentsModelDAO appointmentsModelDAO;
    private long patientCNP;


    @Autowired
    private HttpSession session;

    @GetMapping("/{cabinetId}")
    public String showMakeAnAppointmentPage(@PathVariable int cabinetId, Model model) throws SQLException {

        session.removeAttribute("cabinetId");
        session.setAttribute("cabinetId", cabinetId);

        System.out.println("eroare: <" + model.getAttribute("error") + ">");

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

    @PostMapping("/addAppointment/{id}")
    public String processMakeAppointmentForm(HttpServletRequest request,
                                             @PathVariable int id,
                                             Model model) throws SQLException {

        AppointmentModel appointmentModel = appointmentsModelDAO.findAppoinment(id);

        Appoinments appoinment = new Appoinments();

        appoinment.setId(appointmentsDAO.getMaxAppointmentId() + 1);
        appoinment.setCabinetID(appointmentModel.getCabinetId());
        appoinment.setDoctorCNP(appointmentModel.getDoctorCNP());
        appoinment.setPatientCNP(appointmentModel.getPatientCNP());
        appoinment.setAppointmentTime(appointmentModel.getAppointmentTime());
        appoinment.setExaminationID(appointmentModel.getExaminationID());

        appointmentsDAO.insert(appoinment);

        System.out.println(appoinment);

        appointmentsModelDAO.deleteAll();

        return "redirect:/myAppointments";
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
        idApp = 0;

        DAOFactory daoFactory = new DAOFactory();
        PatientDAO patientDAO = new PatientDAO(daoFactory.getConnection());
        CabinetsScheduleDAO cabinetsScheduleDAO = new CabinetsScheduleDAO(daoFactory.getConnection());
        DoctorsScheduleDAO doctorsScheduleDAO = new DoctorsScheduleDAO(daoFactory.getConnection());
        ExaminationDAO examinationDAO = new ExaminationDAO(daoFactory.getConnection());
        AppointmentsDAO appointmentsDAO = new AppointmentsDAO(daoFactory.getConnection());
        DoctorsSpecialitiesDAO doctorsSpecialitiesDAO = new DoctorsSpecialitiesDAO(daoFactory.getConnection());
        CabinetsDAO cabinetsDAO = new CabinetsDAO(daoFactory.getConnection());
        DoctorDAO doctorDAO = new DoctorDAO(daoFactory.getConnection());
        AppointmentsModelDAO appointmentsModelDAO = new AppointmentsModelDAO(daoFactory.getConnection());

        this.doctorsScheduleDAO = doctorsScheduleDAO;
        this.doctorsSpecialitiesDAO = doctorsSpecialitiesDAO;
        this.appointmentsDAO = appointmentsDAO;
        this.examinationDAO = examinationDAO;
        this.doctorDAO = doctorDAO;
        this.cabinetsScheduleDAO = cabinetsScheduleDAO;
        this.appointmentsModelDAO = appointmentsModelDAO;

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
        if (appointmentDateTime.isBefore(currentDateTime))
            throw new MakeAppointmentException("The specified date and time are in the past!", cabinetID);

        // if the date is on weekend days
        DayOfWeek dayOfWeek = appointmentDateTime.getDayOfWeek();
        String appointmentDayOfWeekString = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
        this.appointmentDayOfWeekString = appointmentDayOfWeekString;
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw new MakeAppointmentException("You cannot make an appointment on weekends!", cabinetID);

        List<CabinetSchedule> cabinetScheduleList = cabinetsScheduleDAO.getCabinetSchedule_FullWeek(cabinetID);

        // verify if it's in cabinet schedule or not
        if(isInCabinetSchedule(appointmentDateTime, cabinetScheduleList))
            throw new MakeAppointmentException("The appointment time is not available in the office schedule!", cabinetID);

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
            appoinmentSlots = recommendDifferentTimeSameDoctor(appoinmentSlots, appointmentDateTime);

            // recommend possible appointments for other doctor, but at the same time
            appoinmentSlots = recommendSameTimeDifferentDaySameDoctor(appoinmentSlots);

            appoinmentSlots = recommendDifferentTimeDifferentDaySameDoctor(appoinmentSlots);

            removeAppointmentsWithSameTime(appoinmentSlots);

            System.out.println("Programari ce trebuie inserate: ");
            for(AppointmentModel newAppointmentModel : appoinmentSlots) {
                System.out.println("\t* id: " + newAppointmentModel.getId() +
                        "\n\t\t- doctorCNP: " + newAppointmentModel.getDoctorCNP() +
                        "\n\t\t- patientCNP: " + newAppointmentModel.getPatientCNP() +
                        "\n\t\t- appointmentTime: " + newAppointmentModel.getAppointmentTime());
            }

            appointmentsModelDAO.deleteAll();
            for(AppointmentModel newAppointmentModel : appoinmentSlots) {
                appointmentsModelDAO.insert(newAppointmentModel);
            }

            model.addAttribute("appoinmentSlots", appoinmentSlots);

            // Verifică dacă lista appointmentSlots este null sau goală
            if (appoinmentSlots == null || appoinmentSlots.isEmpty()) {
                // Dacă este null sau goală, setează un atribut în model pentru a afișa înlocuitorul
                model.addAttribute("showPlaceholder", true);
            } else {
                // Altfel, setează un atribut în model pentru a afișa formularul
                model.addAttribute("showPlaceholder", false);
            }


            MakeAppointmetModel makeAppointmetModel = new MakeAppointmetModel();

            int cabinetId = (int) session.getAttribute("cabinetId");

            makeAppointmetModel.setCabinetID(cabinetId);
            makeAppointmetModel.setCabinetName(cabinetsDAO.getSpecialityName(cabinetId));
            makeAppointmetModel.setDoctorList(doctorsSpecialitiesDAO.getDoctorsHaveSameSpeciality(cabinetId));
            makeAppointmetModel.setExaminationList(examinationDAO.getCabinetExamination(cabinetId));
            makeAppointmetModel.setCabinetScheduleList(cabinetsScheduleDAO.getCabinetSchedule_FullWeek(cabinetId));

            Examination examination = new Examination();

            model.addAttribute("cabinet", makeAppointmetModel);

            return "patient/patientAppointmentRecommandation";
        }
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

                if(doctorStartTime.isBefore(patientStartTime) && patientStartTime.isBefore(doctorEndTime))
                    return false;
                else if (patientStartTime.isBefore(doctorStartTime) && doctorStartTime.isBefore(patientEndTime))
                    return false;
                else if (patientStartTime.equals(doctorStartTime))
                    return false;
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

    public List<AppointmentModel> recommendDifferentTimeDifferentDaySameDoctor(List<AppointmentModel> appoinmentSlots) throws SQLException {
        List<Appoinments> doctorAppointmentsPatientDay = appointmentsDAO.getDoctorAppointments(doctorCnp);

        int appointmentDuration = (int) examinationDAO.getAverageDuration(examinationId);

        if (appointmentDuration == 0)
            appointmentDuration = 30;

        LocalDateTime nextDay = appointmentDateTime.plusDays(1);
        LocalDateTime previousDay = appointmentDateTime.minusDays(1);

        // is the next day is on weekend
        DayOfWeek nextDayOfWeek = nextDay.getDayOfWeek();
        if (nextDayOfWeek == DayOfWeek.SATURDAY)
            nextDay = nextDay.plusDays(2);

        // if the previous day is on weekend
        DayOfWeek prevDayOfWeek = previousDay.getDayOfWeek();
        if (prevDayOfWeek == DayOfWeek.SUNDAY)
            nextDay = previousDay.minusDays(2);

        appoinmentSlots = recommendDifferentTimeSameDoctor(appoinmentSlots, nextDay);
        appoinmentSlots = recommendDifferentTimeSameDoctor(appoinmentSlots, previousDay);

        return  appoinmentSlots;
    }

    public List<AppointmentModel> recommendSameTimeDifferentDaySameDoctor(List<AppointmentModel> appoinmentSlots) throws SQLException {
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<Appoinments> doctorAppointmentsPatientDay = appointmentsDAO.getDoctorAppointments(doctorCnp);

        int appointmentDuration = (int) examinationDAO.getAverageDuration(examinationId);

        if (appointmentDuration == 0)
            appointmentDuration = 30;

        LocalDateTime nextDay = appointmentDateTime.plusDays(1);
        LocalDateTime previousDay = appointmentDateTime.minusDays(1);

        // is the next day is on weekend
        DayOfWeek nextDayOfWeek = nextDay.getDayOfWeek();
        if (nextDayOfWeek == DayOfWeek.SATURDAY)
            nextDay = nextDay.plusDays(2);

        // if the previous day is on weekend
        DayOfWeek prevDayOfWeek = previousDay.getDayOfWeek();
        if (prevDayOfWeek == DayOfWeek.SUNDAY) {
            nextDay = previousDay.minusDays(2);
        }

        if(isSlotAvailable(doctorAppointmentsPatientDay, nextDay, appointmentDuration)) {
            AppointmentModel addAppointment;
            addAppointment = createAppointmentLOCAL(nextDay);
            LocalDateTime addAppointmentDateTime = addAppointment.getAppointmentTime();

            // if date and time are in the past
            if (addAppointmentDateTime.isAfter(currentDateTime)) {
                appoinmentSlots.add(addAppointment);
            }
        }

        if(isSlotAvailable(doctorAppointmentsPatientDay, previousDay, appointmentDuration)) {
            AppointmentModel addAppointment;
            addAppointment = createAppointmentLOCAL(previousDay);
            LocalDateTime addAppointmentDateTime = addAppointment.getAppointmentTime();

            // if date and time are in the past
            if (addAppointmentDateTime.isAfter(currentDateTime)) {
                appoinmentSlots.add(addAppointment);
            }
        }

        return appoinmentSlots;
    }

    public List<AppointmentModel> recommendDifferentTimeSameDoctor(List<AppointmentModel> appoinmentSlots, LocalDateTime appDateTime) throws SQLException {
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<Appoinments> doctorAppointmentsPatientDay = appointmentsDAO.getDoctorAppointments(doctorCnp);

        // keep only the appointments who have the same day as "appDateTime"
        LocalDate appDate = appDateTime.toLocalDate();

        // Filtrați programările care au aceeași zi ca appDateTime
        List<Appoinments> sameDayAppointments = doctorAppointmentsPatientDay.stream()
                .filter(appointment -> appointment.getAppointmentTime().toLocalDate().equals(appDate))
                .collect(Collectors.toList());

        if(!sameDayAppointments.isEmpty()) {

            int appointmentDuration = (int) examinationDAO.getAverageDuration(examinationId);
            if (appointmentDuration == 0)
                appointmentDuration = 30;

            // create a map to store every (startTimeAppointment, endTimeAppointment) from a Doctor's Appointment Schedule
            Map<LocalTime, LocalTime> appointmentsMap = new HashMap<>();

            // Iterați prin lista de programări ale doctorului
            for (Appoinments appointment : sameDayAppointments) {
                LocalDateTime appointmentTime = appointment.getAppointmentTime();
                LocalTime startTime = appointmentTime.toLocalTime();
                LocalTime endTime = startTime.plusMinutes(appointmentDuration);

                // Adăugați startTime-ul și endTime-ul în Map
                appointmentsMap.put(startTime, endTime);
            }

            // sort in ascending order according to the start time of a doctor's appointment
            Map<LocalTime, LocalTime> sortedAppointmentsMap = new TreeMap<>(appointmentsMap);

            // AFISAM programarile doctorului
            for (Map.Entry<LocalTime, LocalTime> entry : sortedAppointmentsMap.entrySet()) {
                LocalTime startTime = entry.getKey();
                LocalTime endTime = entry.getValue();
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

                if (!findStartDay) {
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

            boolean findAnyAppointment = false;
            // iteram pentru a gasi posibilele programari ale pacientului
            for (Map.Entry<LocalTime, Integer> entry : availableTimeMap.entrySet()) {
                LocalTime endTime = entry.getKey();
                int minutesAvailable = entry.getValue();

                if (minutesAvailable > appointmentDuration + 3) {
                    findAnyAppointment = true;
                    AppointmentModel addAppointment;
                    addAppointment = createAppointment(endTime);
                    LocalDateTime addAppointmentDateTime = addAppointment.getAppointmentTime();

                    // if date and time are in the past
                    if (addAppointmentDateTime.isAfter(currentDateTime)) {
                        appoinmentSlots.add(addAppointment);
                    }
                }
            }

            if (!findAnyAppointment) {
                // Extract the day of the week from `appointment Date Time`
                DayOfWeek appointmentDayOfWeek = appDateTime.getDayOfWeek();
                String appointmentDayOfWeekString = appointmentDayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());

                DoctorSchedule doctorSchedule = doctorsScheduleDAO.getDoctorSchedule_SpecificDay(doctorCnp, appointmentDayOfWeekString);

                LocalTime doctorStartDay = doctorSchedule.getStartTime();
                LocalTime doctorEndDay = doctorSchedule.getEndTime();

                if (startDay.minusMinutes(appointmentDuration + 3).isAfter(doctorStartDay)) {
                    AppointmentModel addAppointment;
                    addAppointment = createAppointment(startDay.minusMinutes(appointmentDuration + 3));
                    LocalDateTime addAppointmentDateTime = addAppointment.getAppointmentTime();

                    // if date and time are in the past
                    if (addAppointmentDateTime.isAfter(currentDateTime)) {
                        appoinmentSlots.add(addAppointment);
                    }
                }

                if (finishDay.plusMinutes(appointmentDuration + 3).isBefore(doctorEndDay)) {
                    AppointmentModel addAppointment;
                    addAppointment = createAppointment(finishDay.plusMinutes(appointmentDuration + 3));
                    LocalDateTime addAppointmentDateTime = addAppointment.getAppointmentTime();

                    // if date and time are in the past
                    if (addAppointmentDateTime.isAfter(currentDateTime)) {
                        appoinmentSlots.add(addAppointment);
                    }
                }

            }
        }
        else{ // daca nu are nicio programare in ziua respectiva
            // Extract the day of the week from `appointment Date Time`
            DayOfWeek appointmentDayOfWeek = appDateTime.getDayOfWeek();
            String appointmentDayOfWeekString = appointmentDayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());

            DoctorSchedule doctorSchedule = doctorsScheduleDAO.getDoctorSchedule_SpecificDay(doctorCnp, appointmentDayOfWeekString);

            LocalTime doctorStartDay = doctorSchedule.getStartTime();
            LocalTime doctorEndDay = doctorSchedule.getEndTime();

            AppointmentModel addAppointment;
            addAppointment = createAppointment(doctorStartDay);
            LocalDateTime addAppointmentDateTime = addAppointment.getAppointmentTime();

            // if date and time are in the past
            if (addAppointmentDateTime.isAfter(currentDateTime)) {
                appoinmentSlots.add(addAppointment);
            }

            addAppointment = createAppointment(doctorEndDay);
            addAppointmentDateTime = addAppointment.getAppointmentTime();

            // if date and time are in the past
            if (addAppointmentDateTime.isAfter(currentDateTime)) {
                appoinmentSlots.add(addAppointment);
            }
        }


        return appoinmentSlots;
    }

    public List<AppointmentModel> recommendSameTimeDifferentDoctor(List<AppointmentModel> appoinmentSlots) throws SQLException {
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<Doctor> doctorList = doctorsSpecialitiesDAO.getDoctorsHaveSameSpeciality(cabinetID);

        if(!doctorList.isEmpty()) {

            // delete the doctor from the patient form
            Iterator<Doctor> iterator = doctorList.iterator();
            while (iterator.hasNext()) {
                Doctor doctor = iterator.next();
                if (doctor.getCnp() == doctorCnp) {
                    iterator.remove();
                    break;
                }
            }

            for (Doctor doctor : doctorList) {
                List<Appoinments> doctorAppointmentsPatientDay = appointmentsDAO.getDoctorAppointments(doctor.getCnp());

                int appointmentDuration = (int) examinationDAO.getAverageDuration(examinationId);

                if (appointmentDuration == 0)
                    appointmentDuration = 30;

                if (isSlotAvailable(doctorAppointmentsPatientDay, appointmentDateTime, appointmentDuration)) {

                    AppointmentModel newAppointment = new AppointmentModel();
                    newAppointment.setId(idApp);
                    idApp++;
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

                    LocalDateTime addAppointmentDateTime = newAppointment.getAppointmentTime();

                    // if date and time are in the past
                    if (addAppointmentDateTime.isAfter(currentDateTime)) {
                        appoinmentSlots.add(newAppointment);
                    }
                }
            }
        }

        return appoinmentSlots;
    }

    public AppointmentModel createAppointment(LocalTime startTime) throws SQLException {
        AppointmentModel newAppointment = new AppointmentModel();
        newAppointment.setId(idApp);
        idApp++;
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

    public AppointmentModel createAppointmentLOCAL(LocalDateTime appTime) throws SQLException {
        AppointmentModel newAppointment = new AppointmentModel();
        newAppointment.setId(idApp);
        idApp++;
        newAppointment.setCabinetId(cabinetID);
        newAppointment.setDoctorCNP(doctorCnp);
        newAppointment.setDoctorFirstName(doctorDAO.getFirstName(newAppointment.getDoctorCNP()));
        newAppointment.setDoctorLastName(doctorDAO.getLastName(newAppointment.getDoctorCNP()));
        newAppointment.setPatientCNP(patientCNP);

        LocalDate localDate = appTime.toLocalDate();
        newAppointment.setLocalDate(localDate);

        LocalTime newStartTime = appTime.toLocalTime();
        newAppointment.setLocalTime(newStartTime);


        newAppointment.setAppointmentTime(appTime);
        newAppointment.setExaminationID(examinationId);
        newAppointment.setExaminationName(examinationDAO.getExaminationName(newAppointment.getExaminationID()));

        return newAppointment;
    }

    public void removeAppointmentsWithSameTime(List<AppointmentModel> appointmentSlots) {
        Iterator<AppointmentModel> iterator = appointmentSlots.iterator();

        LocalDateTime prevAppointmentTime = null;

        while (iterator.hasNext()) {
            AppointmentModel appointment = iterator.next();

            LocalDateTime currentAppointmentTime = appointment.getAppointmentTime();

            if (prevAppointmentTime != null && prevAppointmentTime.equals(currentAppointmentTime)) {
                appointmentSlots.remove(iterator);
            } else {
                prevAppointmentTime = currentAppointmentTime;
            }
        }
    }

    @ExceptionHandler(MakeAppointmentException.class)
    public String handleMakeAppointmentException(MakeAppointmentException ex, Model model) throws SQLException {
        model.addAttribute("error", ex.getMessage());
        System.out.println("mesajul de eroare: <" + model.getAttribute("error") + ">, cabientul este: " + model.getAttribute("cabinetId"));
        return showMakeAnAppointmentPage(ex.getCabinetId(), model);
        //        return "redirect:/makeAppointment/{cabinetId}";
    }


}