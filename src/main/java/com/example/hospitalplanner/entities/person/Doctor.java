package com.example.hospitalplanner.entities.person;

import com.example.hospitalplanner.entities.Appoinments;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.schedule.*;
import com.example.hospitalplanner.entities.person.Person;

import java.util.List;

public class Doctor extends Person {
    private List<Cabinet> specialties;
    private List<DoctorSchedule> doctorSchedules;
    private List<Appoinments> appoinmentsList;

    public Doctor() {}

    public Doctor(String email, String password){
        super(email, password);
    }
    public Doctor(long cnp, String firstName, String lastName, char gender, String phoneNumber, String address) {
        super(cnp, firstName, lastName, gender, phoneNumber, address);
    }

    public List<Cabinet> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Cabinet> specialties) {
        this.specialties = specialties;
    }

    public void addSpecialty(Cabinet specialty){
        if (specialties.contains(specialty))
            throw new IllegalArgumentException("This specialty already exists in the specialties list");

        specialties.add(specialty);
    }

    public void removeSpecialty(Cabinet specialty){
        specialties.remove(specialty);
    }

    public List<DoctorSchedule> getDoctorSchedule() {
        return doctorSchedules;
    }

    public void setDoctorSchedule(List<DoctorSchedule> doctorSchedules) {
        this.doctorSchedules = doctorSchedules;
    }

    public void addDoctorSchedule(DoctorSchedule doctorSchedule){
        if (doctorSchedules.contains(doctorSchedule))
            throw new IllegalArgumentException("This doctorSchedule already exists in the doctorSchedules list");

        doctorSchedules.add(doctorSchedule);
    }

    public void removeDoctorSchedule(DoctorSchedule doctorSchedule){
        doctorSchedules.remove(doctorSchedule);
    }

    public List<Appoinments> getAppoinmentsList() {
        return appoinmentsList;
    }

    @Override
    public void setAppoinments(List<Appoinments> appoinments) {
        super.setAppoinments(appoinments);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Doctor (" +
                "CNP = '" + cnp + "')" +
                "\n\t\t- FIRST_NAME = '" + firstName + '\'' +
                "\n\t\t- LAST_NAME = '" + lastName + '\'' +
                "\n\t\t- GENDER = '" + gender + '\'' +
                "\n\t\t- PHONE_NUMBER= '" + phoneNumber + '\'' +
                "\n\t\t- EMAIL = '" + email + '\'' +
                "\n\t\t- ADDRESS = '" + address + '\'' +
                "\n"
        );
//
//        sb.append("\nDoctor Specialties:");
//
//        for (Cabinet cabinet : this.specialties) {
//            sb.append("\t" + cabinet.getSpecialtyName());
//            sb.append("\n");
//        }
//
//        sb.append("\nDoctor Schedules:");
//
//        for (DoctorSchedule doctorSchedule : this.doctorSchedules) {
//            sb.append("\t" + doctorSchedule.toString());
//            sb.append("\n");
//        }
//
//        sb.append("\nDoctor Appoinments:");
//
//        for (Appoinments appoinments : this.appoinmentsList) {
//            sb.append("\t" + appoinments.toString());
//            sb.append("\n");
//        }

        return sb.toString();
    }
}
