package com.example.mareu.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.mareu.data.MyDatabase;
import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Retrieves data base of meetings and rooms.
 * performs filters and sorts on data in order to pass on to the viewModels
 */
public class MeetingRepository {
    private MutableLiveData<List<Meeting>> allMeetings;
    private MutableLiveData<List<Room>> allRooms;
    private MyDatabase myDatabase;

    /**
     * Constructor
     * retrieves a fake data base from the class MyDatabase
     * initialises mutable live data for rooms and meetings
     */
    public MeetingRepository() {
        myDatabase = new MyDatabase();
        allRooms = new MutableLiveData<>(new ArrayList<>());
        allRooms.setValue(myDatabase.getAllRooms());
        allMeetings = new MutableLiveData<>(new ArrayList<>());
        allMeetings.setValue(myDatabase.getAllMeetings());
    }

    /**
     * @return all meetings
     */
    public MutableLiveData<List<Meeting>> getAllMeetings(){
        allMeetings.setValue(myDatabase.getAllMeetings());
        System.out.println("getting all meetings and there are " + allMeetings.getValue().size());
        return allMeetings;
    }

    /**
     * Used to filter meetings by date or room.
     * can filter date by: year, month, hour, minute
     * @param type used for deciding the type of filter:"room", "year", "month", "hour", "minute"
     * @param value what will be used for the filter
     * @return filtered meetings
     */
    public MutableLiveData<List<Meeting>> getFilteredMeetings(String type, String value) {
        MutableLiveData<List<Meeting>> filteredMeeting = new MutableLiveData<>(new ArrayList<>());;
        List<Meeting> filteredMeetingList = new ArrayList<>();
        List<Meeting> allMeetingList = allMeetings.getValue();
        DateTimeFormatter formatter;
        for(int i =0; i < allMeetingList.size(); i++) {
            if(type.equals("room")) {
                if(String.valueOf(allMeetingList.get(i).getLocation().getRoomNumber()).equals(value))
                    filteredMeetingList.add(allMeetingList.get(i));
            }
            else{
                switch (type) {
                    case "year":
                        formatter = DateTimeFormatter.ofPattern("yy");
                        break;
                    case "month":
                        formatter = DateTimeFormatter.ofPattern("yy/MM");
                        break;
                    case "day":
                        formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
                        break;
                    case "hour":
                        formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH");
                        break;
                    case "minute":
                        formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");
                        break;
                    default:
                        value = "23/03/02 12:00";
                        formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");
                }
                if (allMeetingList.get(i).getDate().format(formatter).equals(value))
                    filteredMeetingList.add(allMeetingList.get(i));
            }
        }
        filteredMeeting.setValue(filteredMeetingList);
        return filteredMeeting;
    }

    /**
     *
     * sort meetings by room in ascending order
     * @param type by "room" or by "time"
     * @return sorted meetings
     */
    public MutableLiveData<List<Meeting>> getSortedMeetings(String type){
        MutableLiveData<List<Meeting>> sortedMeeting = new MutableLiveData<>(new ArrayList<>());;
        List<Meeting> allMeetingList = allMeetings.getValue();
        allMeetingList.sort(new Comparator<Meeting>() {
            @Override
            public int compare(Meeting meeting, Meeting t1) {
                int comparison = -1;
                switch (type) {
                    case "time":
                        comparison = meeting.getDate().toString().compareTo(t1.getDate().toString());
                        break;
                    case "room":
                        comparison = meeting.getLocation().getRoomNumber()-t1.getLocation().getRoomNumber();
                        break;
                    default:
                        comparison = (int)(meeting.getId()-t1.getId());;


                }
                return comparison;
            }
        });
        sortedMeeting.setValue(allMeetingList);
        return sortedMeeting;
    }

    /**
     * @return all the rooms
     */
    public MutableLiveData<List<Room>> getAllRooms() {
        return allRooms;
    }

    /**
     * used to only rieve the room numbers as an array of Strings
     * @return a list of strings containing the room numbers
     */
    public String[] getRoomNumbers(){
        List<Room> rooms = allRooms.getValue();
        String[] roomNumbers = new String[rooms.size()];
        for(int i = 0; i < rooms.size(); i++){
            roomNumbers[i]=Integer.toString(rooms.get(i).getRoomNumber());
        }
        return roomNumbers;
    }

    /**
     * removes a Meeting by id
     * @param meetingId
     */
    public void deleteMeeting(long meetingId) {

        myDatabase.deleteMeeting(meetingId);
        allMeetings.setValue(myDatabase.getAllMeetings());
    }
    /**
     * Used to create a new Meeting with:
     * @param date of new meeting
     * @param location of meeting
     * @param subject of meeting
     * @param participants List of participants as a string
     * @return the status of the validation
     */
    public String addMeeting(LocalDateTime date, Room location, String subject, List<String> participants){
        String validation = myDatabase.addMeeting(date,location, subject, participants);
        allMeetings.setValue(myDatabase.getAllMeetings());
        return validation;
    }

    /**
     * Used to create a new Room
     * @param roomNumber
     * @param maxSize
     */
    public void addRoom(int roomNumber, int maxSize){//is not used at the moment but could be implemented if the feature was needed
       myDatabase.addRoom(roomNumber,maxSize);
       allRooms.setValue(myDatabase.getAllRooms());
    }


}
