package com.example.hospitalplanner.models;

import com.example.hospitalplanner.entities.Examination;
import com.example.hospitalplanner.entities.person.Doctor;
import com.example.hospitalplanner.entities.schedule.CabinetSchedule;

import java.util.ArrayList;
import java.util.List;

public class MakeAppointmetModel {
    private String cabinetName;
    private List<Doctor> doctorList = new ArrayList<>();
    private List<Examination> examinationList = new ArrayList<>();
    private List<CabinetSchedule> cabinetScheduleList = new ArrayList<>();

    public MakeAppointmetModel() {}

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public List<Examination> getExaminationList() {
        return examinationList;
    }

    public void setExaminationList(List<Examination> examinationList) {
        this.examinationList = examinationList;
    }

    public List<CabinetSchedule> getCabinetScheduleList() {
        return cabinetScheduleList;
    }

    public void setCabinetScheduleList(List<CabinetSchedule> cabinetScheduleList) {
        this.cabinetScheduleList = cabinetScheduleList;
    }
}
