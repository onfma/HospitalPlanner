package com.example.hospitalplanner.entities;

import org.json.JSONObject;

import java.util.Formatter;
import java.util.Objects;

public class Cabinet {
    private int id;
    private String specialtyName;


    public Cabinet() {}

    public Cabinet (int id, String specialtyName){
        this.id = id;
        this.specialtyName = specialtyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cabinet cabinet = (Cabinet) o;
        return id == cabinet.id &&
                Objects.equals(specialtyName, cabinet.specialtyName);
    }

    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("specialtyName", specialtyName);
        return jsonObject.toString();
    }
}
