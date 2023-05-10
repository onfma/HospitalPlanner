package com.example.hospitalplanner.test;

import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.Schedule;

import java.time.LocalTime;

public class TestSchedule {
    public TestSchedule(){

    }

    private void constructorSchedule(){

        // Incorrect dayOfWeek
        CabinetSchedule cabinetSchedule = new CabinetSchedule(1, "Monday", LocalTime.of(9,0), LocalTime.of(9,0));
    }
}
