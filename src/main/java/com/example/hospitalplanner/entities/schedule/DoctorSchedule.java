package com.example.hospitalplanner.entities.schedule;

import java.time.LocalTime;
import java.util.Objects;

public class DoctorSchedule extends Schedule {
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
        return "Doctor Schedule{" +
                "doctor_CNP=" + id +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
