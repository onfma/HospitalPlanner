package com.example.hospitalplanner.entities.schedule;

import org.json.JSONObject;

import java.time.LocalTime;
import java.util.Objects;

public class DoctorSchedule extends Schedule {
    public DoctorSchedule(){
    }
    public DoctorSchedule(long doctorCNP, String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        super(doctorCNP, dayOfWeek, startTime, endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorSchedule)) return false;
        Schedule doctorSchedule = (DoctorSchedule) o;
        return id == doctorSchedule.id &&
                Objects.equals(dayOfWeek, doctorSchedule.dayOfWeek) &&
                Objects.equals(startTime, doctorSchedule.startTime) &&
                Objects.equals(endTime, doctorSchedule.endTime);
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doctor_CNP", id);
        jsonObject.put("dayOfWeek", dayOfWeek);
        jsonObject.put("startTime", startTime.toString());
        jsonObject.put("endTime", endTime.toString());
        return jsonObject.toString();

//        return "Doctor Schedule{" +
//                "doctor_CNP=" + id +
//                ", dayOfWeek='" + dayOfWeek + '\'' +
//                ", startTime=" + startTime +
//                ", endTime=" + endTime +
//                '}';
    }
}
