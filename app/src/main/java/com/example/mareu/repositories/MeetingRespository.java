package com.example.mareu.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.models.Time;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeetingRespository {
    private MutableLiveData<List<Meeting>> allMeetings;
    private MutableLiveData<List<Room>> allRooms;
    //private static MeetingRespository instance;

    public MeetingRespository(Application application)
    {
        getAllMeetings();
    }
    /*public static MeetingRespository getInstance() {
        if(instance == null){
            instance = new MeetingRespository();
        }
        return instance;
    }*/
    public MutableLiveData<List<Meeting>> getAllMeetings(){
        generateSomeRooms();
        generateRandomMeetings();
        return allMeetings;
    }

    public void addMeeting(Time time, Room location, String subject, List<String> participants){
        List<Meeting> meetings = allMeetings.getValue();
        meetings.add(
            new Meeting(
                time,
                location,
                subject,
                participants
            )
        );

        allMeetings.setValue(meetings);

    }
    public void addRoom(int roomNumber, int maxSize){
        List<Room> rooms = allRooms.getValue();
        rooms.add(new Room(roomNumber,maxSize));
        allRooms.setValue(rooms);
    }

    //used to fill create rooms and meetings for testing the app
    private void generateSomeRooms(){
        List<Room> rooms = allRooms.getValue();
        for(int i = 101; i <= 110; i++){
            addRoom(i,10);
        }
        allRooms.setValue(rooms);
    }
    private void generateRandomMeetings(){
        addMeeting(
                new Time(14,30),
                allRooms.getValue().get(1),
                "future changes",
                Arrays.asList("John.do@gmail.com", "person2@gmail.com", "person3@gmail.com")
        );
        addMeeting(
                new Time(9,15),
                allRooms.getValue().get(7),
                "investements",
                Arrays.asList("person2@gmail.com", "person3@gmail.com")
        );
        addMeeting(
                new Time(18,0),
                allRooms.getValue().get(4),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );

    }
}