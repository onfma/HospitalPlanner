package com.example.hospitalplanner.test;

import com.example.hospitalplanner.entities.schedule.CabinetSchedule;
import com.example.hospitalplanner.entities.schedule.Schedule;

import java.time.LocalTime;

public class TestSchedule {
    public TestSchedule(){
        constructorSchedule();
    }

    private void constructorSchedule(){

        // Incorrect dayOfWeek
        CabinetSchedule cabinetSchedule1 = new CabinetSchedule(1, "Monday@", LocalTime.of(9,0), LocalTime.of(12,0));

        // Incorrect: startTime > endTime
        CabinetSchedule cabinetSchedule2 = new CabinetSchedule(1, "Monday", LocalTime.of(13,0), LocalTime.of(12,0));

        // Incorrect: startTime = endTime
        CabinetSchedule cabinetSchedule3 = new CabinetSchedule(1, "Monday", LocalTime.of(12,0), LocalTime.of(12,0));

        // Correct
        CabinetSchedule cabinetSchedule = new CabinetSchedule(1, "Monday", LocalTime.of(12,0), LocalTime.of(12,20));

    }
}
