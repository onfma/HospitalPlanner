package com.example.hospitalplanner.entities.person;

import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.schedule.*;
import com.example.hospitalplanner.entities.person.Person;

import java.util.List;

public class Doctor extends Person {
    private List<Cabinet> specialties;
    private List<DoctorSchedule> doctorSchedules;

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
        doctorSchedules.add(doctorSchedule);
    }

    public void removeDoctorSchedule(DoctorSchedule doctorSchedule){
        doctorSchedules.remove(doctorSchedule);
    }
}
