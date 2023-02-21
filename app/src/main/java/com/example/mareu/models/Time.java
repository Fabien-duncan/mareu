package com.example.mareu.models;

public class Time {
    private int hours;
    private int minutes;

    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        String mins;
        if(minutes < 10) mins = "0" + minutes;
        else mins = String.valueOf(minutes);
        return hours + "hr" + mins;
    }
}
