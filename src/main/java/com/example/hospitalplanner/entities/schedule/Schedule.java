package com.example.hospitalplanner.entities.schedule;


import java.time.LocalTime;

public abstract class Schedule {
    protected int id;
    protected String dayOfWeek;
    protected LocalTime startTime;
    protected LocalTime endTime;

    public Schedule(int id, String dayOfWeek, LocalTime startTime, LocalTime endTime){
        if (!String.valueOf(id).matches("\\d+"))
            throw new IllegalArgumentException("ID should only contain digits.");
        if (!dayOfWeek.matches("[a-zA-Z]+") || !(dayOfWeek.equals("Monday") || dayOfWeek.equals("Tuesday") || dayOfWeek.equals("Wednesday") || dayOfWeek.equals("Thursday") || dayOfWeek.equals("Friday")))
            throw new IllegalArgumentException("Day of week should contain only letters and be one of the following: 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'");
        if (startTime.getMinute() < 0 || startTime.getMinute() > 59 || startTime.getHour() < 0 || startTime.getHour() > 23)
            throw new IllegalArgumentException("Start times should contain only hours and minutes in the range 00:00 - 23:59.");
        if (endTime.getMinute() < 0 || endTime.getMinute() > 59 || endTime.getHour() < 0 || endTime.getHour() > 23)
            throw new IllegalArgumentException("End times should contain only hours and minutes in the range 00:00 - 23:59.");
        if (endTime.getMinute() < 0 || endTime.getMinute() > 59 || endTime.getHour() < 0 || endTime.getHour() > 23)
            throw new IllegalArgumentException("End times should contain only hours and minutes in the range 00:00 - 23:59.");
        if (startTime.isAfter(endTime))
            throw new IllegalArgumentException("Start time cannot be after end time.");


        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
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
        if (startTime.getMinute() < 0 || startTime.getMinute() > 59 || startTime.getHour() < 0 || startTime.getHour() > 23)
            throw new IllegalArgumentException("Start times should contain only hours and minutes in the range 00:00 - 23:59.");

        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        if (endTime.getMinute() < 0 || endTime.getMinute() > 59 || endTime.getHour() < 0 || endTime.getHour() > 23)
            throw new IllegalArgumentException("End times should contain only hours and minutes in the range 00:00 - 23:59.");

        this.endTime = endTime;
    }
}
