package com.example.hospitalplanner.entities.schedule;


import java.time.LocalTime;

public abstract class Schedule {
    protected long id;
    protected String dayOfWeek;
    protected LocalTime startTime;
    protected LocalTime endTime;

    public Schedule(long id, String dayOfWeek, LocalTime startTime, LocalTime endTime){
        if (!String.valueOf(id).matches("\\d+"))
            throw new IllegalArgumentException("ID should only contain digits.");
        if (!dayOfWeek.matches("[a-zA-Z]+") || !(dayOfWeek.equals("Monday") || dayOfWeek.equals("Tuesday") || dayOfWeek.equals("Wednesday") || dayOfWeek.equals("Thursday") || dayOfWeek.equals("Friday")))
            throw new IllegalArgumentException("Day of week should contain only letters and be one of the following: 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'");
        if (startTime.isAfter(endTime))
            throw new IllegalArgumentException("Start time cannot be after end time.");
        if (startTime.equals(endTime))
            throw new IllegalArgumentException("Start time cannot be equals to end time.");

        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        if (!dayOfWeek.matches("[a-zA-Z]+") || !(dayOfWeek.equals("Monday") || dayOfWeek.equals("Tuesday") || dayOfWeek.equals("Wednesday") || dayOfWeek.equals("Thursday") || dayOfWeek.equals("Friday")))
            throw new IllegalArgumentException("Day of week should contain only letters and be one of the following: 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'");

        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        if (startTime.isAfter(endTime))
            throw new IllegalArgumentException("Start time cannot be after end time.");
        if (startTime.equals(endTime))
            throw new IllegalArgumentException("Start time cannot be equals to end time.");

        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        if (startTime.isAfter(endTime))
            throw new IllegalArgumentException("Start time cannot be after end time.");
        if (startTime.equals(endTime))
            throw new IllegalArgumentException("Start time cannot be equals to end time.");

        this.endTime = endTime;
    }
}
