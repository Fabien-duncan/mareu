package com.example.mareu.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.mareu.data.MyDatabase;
import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.models.Time;

import java.util.ArrayList;
import java.util.List;

public class MeetingRepository {
    private MutableLiveData<List<Meeting>> allMeetings;



    private MutableLiveData<List<Room>> allRooms;
    private long maxId = 0;
    private MyDatabase myDatabase;
    public MeetingRepository() {
        myDatabase = new MyDatabase();
        allRooms = new MutableLiveData<>(new ArrayList<>());
        allRooms.setValue(myDatabase.getAllRooms());
        allMeetings = new MutableLiveData<>(new ArrayList<>());
        allMeetings.setValue(myDatabase.getAllMeetings());
    }

    /*public static MeetingRespository getInstance() {
        if(instance == null){
            instance = new MeetingRespository();
        }
        return instance;
    }*/
    public MutableLiveData<List<Meeting>> getAllMeetings(){
        return allMeetings;
    }
    public MutableLiveData<List<Room>> getAllRooms() {
        return allRooms;
    }
    public void deleteMeeting(long meetingId) {

        myDatabase.deleteMeeting(meetingId);
        allMeetings.setValue(myDatabase.getAllMeetings());
    }
    public void addMeeting(Time time, Room location, String subject, List<String> participants){
        myDatabase.addMeeting(time,location, subject, participants);
        allMeetings.setValue(myDatabase.getAllMeetings());

    }
    public void addRoom(int roomNumber, int maxSize){
       myDatabase.addRoom(roomNumber,maxSize);
       allRooms.setValue(myDatabase.getAllRooms());
    }

}
