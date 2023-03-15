package com.example.mareu.data;

import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used as a fake database to provide and store data for the apllication
 * It creates some pre existing meetings in order to be able to test the application
 */
public class MyDatabase {
    public List<Meeting> mAllMeetings;
    public List<Room> mAllRooms;
    private long maxId = 0;

    /**
     * Constructor method
     * generates some Rooms and random meetings
     */
    public MyDatabase() {
        mAllMeetings = new ArrayList<>();
        mAllRooms = new ArrayList<>();
        generateSomeRooms();
        generateRandomMeetings();
    }

    /**
     * @return a list of all the meetings
     */
    public List<Meeting> getAllMeetings() {
        return mAllMeetings;
    }

    /**
     * @return the List of Rooms
     */
    public List<Room> getAllRooms() {
        return mAllRooms;
    }

    /**
     * Deletes a meeting by id
     * @param meetingId id of meeting to delete
     */
    public void deleteMeeting(long meetingId) {

        if (mAllMeetings == null) return;

        for(int i = 0; i < mAllMeetings.size(); i++){
            if(mAllMeetings.get(i).getId() == meetingId)
            {
                mAllMeetings.remove(i);
            }
        }
    }

    /**
     * Adds a valid meeting to List of Meeting
     * in order for the meeting to be valid it checks the email addresses in the List of Participants of the meeting
     * @param date a LocaleDateTime object
     * @param location a Room object
     * @param subject
     * @param participants a List of participants
     * @return validation message
     */
    public String addMeeting(LocalDateTime date, Room location, String subject, List<String> participants){
        Meeting newMeeting = new Meeting(
                maxId++,
                date,
                location,
                subject,
                participants
        );
        String validation = newMeeting.validateEmailList();
        if(validation.equals("valid"))mAllMeetings.add(newMeeting);
        return validation;
    }

    /**
     * Adds a new Room to the List of Rooms
     * @param roomNumber
     * @param maxSize
     */
    public void addRoom(int roomNumber, int maxSize){
        mAllRooms.add(new Room(roomNumber,maxSize));
    }

    //used to create rooms and meetings for testing the app
    private void generateSomeRooms(){
        for(int i = 101; i <= 110; i++){
            addRoom(i,10);
        }
    }
    //Generates some meetings for testing purposes
    private void generateRandomMeetings(){
        addMeeting(
                LocalDateTime.of(2022,2,12,14,45),
                mAllRooms.get(8),
                "future changes",
                Arrays.asList("John.do@gmail.com", "person2@gmail.com", "person3@gmail.com")
        );
        addMeeting(
                LocalDateTime.of(2022,2,12,15,30),
                mAllRooms.get(8),
                "investements",
                Arrays.asList("person2@gmail.com", "person3@gmail.com")
        );
        addMeeting(
                LocalDateTime.of(2022,2,12,7,20),
                mAllRooms.get(6),
                "recrutment",
                Arrays.asList("person2@gmail.com","person1@gmail.com", "person3@gmail.com")
        );
        addMeeting(
                LocalDateTime.of(2022,2,12,15,45),
                mAllRooms.get(4),
                "investments",
                Arrays.asList("person5@gmail.com","person2@gmail.com", "person3@gmail.com", "person7@gmail.com")
        );
        addMeeting(
                LocalDateTime.of(2023,3,24,15,0),
                mAllRooms.get(8),
                "recrutement",
                Arrays.asList("person3@gmail.com","person2@gmail.com", "person8@gmail.com")
        );
        addMeeting(
                LocalDateTime.of(2023,3,27,15,0),
                mAllRooms.get(8),
                "recrutement",
                Arrays.asList("person2@gmail.com","person6@gmail.com", "person3@gmail.com")
        );
        addMeeting(
                LocalDateTime.now().plusHours(3),
                mAllRooms.get(8),
                "investments",
                Arrays.asList("person5@gmail.com","person2@gmail.com", "person1@gmail.com")
        );
        addMeeting(
                LocalDateTime.of(2023,2,25,15,0),
                mAllRooms.get(8),
                "redundancy",
                Arrays.asList("person2@gmail.com", "person3@gmail.com")
        );
        addMeeting(
                LocalDateTime.of(2022,2,12,15,45),
                mAllRooms.get(5),
                "investments",
                Arrays.asList("person5@gmail.com","person2@gmail.com", "person3@gmail.com", "person7@gmail.com")
        );
    }
}
