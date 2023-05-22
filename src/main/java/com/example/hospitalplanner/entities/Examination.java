package com.example.hospitalplanner.entities;

import java.util.ArrayList;
import java.util.List;

public class Examination {
    private int idExamination;
    private int idCabinet;
    private String examinationName;
    private List<Integer> durations = new ArrayList<>();
    private float averageDuration;

    public Examination() {}

    public Examination(int idExamination, int idCabinet, String examinationName){
        this.idExamination = idExamination;
        this.idCabinet = idCabinet;
        this.examinationName = examinationName;
    }

    public int getIdExamination() {
        return idExamination;
    }

    public void setIdExamination(int idExamination) {
        this.idExamination = idExamination;
    }

    public int getIdCabinet() {
        return idCabinet;
    }

    public void setIdCabinet(int idCabinet) {
        this.idCabinet = idCabinet;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }

    public float getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(float averageDuration) {
        this.averageDuration = averageDuration;
    }

    public List<Integer> getDurations() {
        return durations;
    }

    public void setDurations(List<Integer> durations) {
        this.durations = durations;
    }

    public void addDurations(int duration1) {
        this.durations.add(duration1);

        int sum = 0;
        for (int duration2 : durations) {
            sum += duration2;
        }
        this.averageDuration = sum / durations.size();
    }
}
