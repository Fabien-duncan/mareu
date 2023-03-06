package com.example.mareu.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Meeting {
    private final long id;
    private Time time;

    private LocalDateTime date;
    private Room location;
    private String subject;
    private List<String> participants;

    public Meeting(long id, LocalDateTime date, Room location, String subject, List<String> participants) {
        this.date = date;
        this.location = location;
        this.subject = subject;
        this.participants = participants;
        this.id = id;

    }
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    /*public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }*/

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
    public String detailsToString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");
        return location.getRoomNumber() + " - " + date.format(formatter) + " - " + subject;
    }
    public String participantsToString(){
        String sParticipants = "";
        for(String item: participants){
            sParticipants += item + ", ";
        }
        return sParticipants;
    }

    public long getId() {
        return id;
    }
}
