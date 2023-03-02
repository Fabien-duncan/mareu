package com.example.mareu.data;

import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.models.Time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDatabase {
    public List<Meeting> mAllMeetings;
    public List<Room> mAllRooms;
    private long maxId = 0;

    public MyDatabase() {
        mAllMeetings = new ArrayList<>();
        mAllRooms = new ArrayList<>();
        generateSomeRooms();
        generateRandomMeetings();
    }

    public List<Meeting> getAllMeetings() {
        return mAllMeetings;
    }

    public void setAllMeetings(List<Meeting> allMeetings) {
        mAllMeetings = allMeetings;
    }

    public List<Room> getAllRooms() {
        return mAllRooms;
    }

    public void setAllRooms(List<Room> allRooms) {
        mAllRooms = allRooms;
    }
    public void deleteMeeting(long meetingId) {

        if (mAllMeetings == null) return;

        for(int i = 0; i < mAllMeetings.size(); i++){
            if(mAllMeetings.get(i).getId() == meetingId)
            {
                mAllMeetings.remove(i);
            }
        }
    }
    public void addMeeting(Time time, Room location, String subject, List<String> participants){
        mAllMeetings.add(
                new Meeting(
                        maxId++,
                        time,
                        location,
                        subject,
                        participants
                )
        );
    }
    public void addRoom(int roomNumber, int maxSize){
        mAllRooms.add(new Room(roomNumber,maxSize));
    }

    //used to create rooms and meetings for testing the app
    private void generateSomeRooms(){
        for(int i = 101; i <= 110; i++){
            addRoom(i,10);
        }
    }
    private void generateRandomMeetings(){
        addMeeting(
                new Time(14,30),
                mAllRooms.get(1),
                "future changes",
                Arrays.asList("John.do@gmail.com", "person2@gmail.com", "person3@gmail.com")
        );
        addMeeting(
                new Time(9,15),
                mAllRooms.get(7),
                "investements",
                Arrays.asList("person2@gmail.com", "person3@gmail.com")
        );
        addMeeting(
                new Time(15,0),
                mAllRooms.get(4),
                "recrutment",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        addMeeting(
                new Time(15,45),
                mAllRooms.get(6),
                "redundancy",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com", "person7@gmail.com")
        );

    }
}
