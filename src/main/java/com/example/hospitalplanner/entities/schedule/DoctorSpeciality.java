package com.example.hospitalplanner.entities.schedule;

import org.json.JSONObject;

import java.time.LocalTime;
import java.util.Objects;

public class DoctorSpeciality {
    private int cabinetID;
    private long doctorCNP;

    public DoctorSpeciality() {}
    public DoctorSpeciality(int cabinetID, long doctorCNP){
        this.cabinetID = cabinetID;
        this.doctorCNP = doctorCNP;
    }

    public long getDoctorCNP() {
        return doctorCNP;
    }

    public void setDoctorCNP(long doctorCNP) {
        this.doctorCNP = doctorCNP;
    }

    public int getCabinetID() {
        return cabinetID;
    }

    public void setCabinetID(int cabinetID) {
        this.cabinetID = cabinetID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorSpeciality)) return false;
        return cabinetID == ((DoctorSpeciality) o).cabinetID &&
                Objects.equals(doctorCNP, ((DoctorSpeciality) o).doctorCNP);
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cabinetID", cabinetID);
        jsonObject.put("doctorCNP", doctorCNP);
        return jsonObject.toString();
    }
}

