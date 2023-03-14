package com.example.mareu.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * class to create meetings.
 * Contains: id, a date, a location (as a room object), the subject of the meeting,
 * a List of participants.
 */
public class Meeting {
    private final long id;
    private LocalDateTime date;
    private Room location;
    private String subject;
    private List<String> participants;

    /**
     * Constructor
     * @param id unique number given to each meeting
     * @param date an object of type LocaleDateTime used to store the date
     * @param location a Room obejct used to store the location of the meeting
     * @param subject what the meeting will be about
     * @param participants a list of email addresses of the participants
     */
    public Meeting(long id, LocalDateTime date, Room location, String subject, List<String> participants) {
        this.date = date;
        this.location = location;
        this.subject = subject;
        this.participants = participants;
        this.id = id;
        checkIfSoon(LocalDateTime.now().plusDays(1));
    }

    /**
     * @return the date of the meeting
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * @return the location of the meeting as an instance eof a Room
     */
    public Room getLocation() {
        return location;
    }

    /**
     * @return a String value containing all the information about the meeting except for the participants
     */
    public String detailsToString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        return location.getRoomNumber() + " - " + date.format(formatter) + " - " + subject;
    }

    /**
     * @return a String value containing all the participants separated by comas
     */
    public String participantsToString(){
        String sParticipants = "";
        for(String item: participants){
            sParticipants += item + ", ";
        }
        return sParticipants;
    }

    /**
     * Method used to check if a meeting is soon. It looks at the date of the meeting
     * and checks if it is after now and before the end parameter
     * @param end the uperbound of the range check to find out if the meeting is soon.
     * @return true if the meeting is in between the range of now and end
     */
    public boolean checkIfSoon(LocalDateTime end){

        if(date.isAfter(LocalDateTime.now()) && date.isBefore(end))
        {
            return true;
        }
        else
        {
            //System.out.println( date + " does not lie between " + LocalDateTime.now() +  " and " + end);
            return false;
        }
    }

    /**
     * Used for accessibility purposes.
     * @return a string of the details with extra descriptive words for the text to speech.
     */
    public String detailsForContentDescription(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        return "Sale " + location.getRoomNumber() + " date/heure " + date.format(formatter) + " sujet " + subject;
    }

    /**
     * @return the id of the meeting
     */
    public long getId() {
        return id;
    }
}
