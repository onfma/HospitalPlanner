package com.example.hospitalplanner.entities.schedule;

import java.time.LocalTime;
import java.util.Objects;

public class CabinetSchedule extends Schedule {
    public CabinetSchedule(int cabinetID, String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        super(cabinetID, dayOfWeek, startTime, endTime);
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
        return "Cabinet Schedule {" +
                "cabinet_ID=" + id +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
