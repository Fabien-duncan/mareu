package com.example.mareu.models;

import java.time.LocalTime;
import java.util.List;

public class Meeting {
    private Time time;
    private Room location;
    private String subject;
    private List<String> participants;

    public Meeting(Time time, Room location, String subject, List<String> participants) {
        this.time = time;
        this.location = location;
        this.subject = subject;
        this.participants = participants;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

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
        return location.getRoomNumber() + " - " + time.toString() + " - " + subject;
    }
    public String participantsToString(){
        String sParticipants = "";
        for(String item: participants){
            sParticipants += item + ", ";
        }
        return sParticipants;
    }
}
