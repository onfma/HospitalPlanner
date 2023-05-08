package com.example.hospitalplanner.entities.schedule;


import java.time.LocalTime;

public abstract class Schedule {
    protected int id;
    protected String dayOfWeek;
    protected LocalTime startTime;
    protected LocalTime endTime;

    public Schedule(int id, String dayOfWeek, LocalTime startTime, LocalTime endTime){
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
