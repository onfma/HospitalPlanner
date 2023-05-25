package com.example.hospitalplanner.models;

import com.example.hospitalplanner.database.DAO.DoctorsSpecialitiesDAO;
import com.example.hospitalplanner.entities.Cabinet;
import com.example.hospitalplanner.entities.schedule.DoctorSpeciality;

import java.util.List;

public class DoctorModel {
    private long cnp;
    protected String firstName;
    protected String lastName;
    private String specialityName;
    private List<String> specialityList;

    public DoctorModel() {}

    public DoctorModel(String firstName, String lastName, String specialityName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialityName = specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSpecialityName() {
        return specialityName;
    }
    public void setCnp(long cnp) {
        this.cnp = cnp;
    }
    public long getCnp() {
        return cnp;
    }
    public List<String> getSpecialityList() {
        return specialityList;
    }
    public void setSpecialityList(List<String> specialityList) {
        this.specialityList = specialityList;
    }
}
