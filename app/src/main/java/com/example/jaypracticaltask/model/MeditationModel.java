package com.example.jaypracticaltask.model;

public class MeditationModel {
    public String name,description,steps,duration;

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getSteps() {
        return steps;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
