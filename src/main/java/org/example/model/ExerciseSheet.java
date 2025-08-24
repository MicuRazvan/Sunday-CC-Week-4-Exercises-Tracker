package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ExerciseSheet {
    private String name;
    private List<ExerciseDay> days;

    public ExerciseSheet(String name) {
        this.name = name;
        this.days = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExerciseDay> getDays() {
        return days;
    }

    public void setDays(List<ExerciseDay> days) {
        this.days = days;
    }

    public void addDay(ExerciseDay day) {
        this.days.add(day);
    }
}
