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
        allMeetings.setValue(myDatabase.getAllMeetings());
        System.out.println("getting all meetings and there are " + allMeetings.getValue().size());
        return allMeetings;
    }
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
    //filters by room
    public MutableLiveData<List<Meeting>> getFilteredMeetings(String room){
        MutableLiveData<List<Meeting>> filteredMeeting = new MutableLiveData<>(new ArrayList<>());;
        List<Meeting> filteredMeetingList = new ArrayList<>();
        List<Meeting> allMeetingList = allMeetings.getValue();
        for(int i =0; i < allMeetingList.size(); i++){
            if(allMeetingList.get(i).getLocation().getRoomNumber() == Integer.parseInt(room)){
                filteredMeetingList.add(allMeetingList.get(i));
            }
        }
        filteredMeeting.setValue(filteredMeetingList);
        return filteredMeeting;
    }
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
    public MutableLiveData<List<Meeting>> getSortedMeetingsRoom(){
        MutableLiveData<List<Meeting>> sortedMeeting = new MutableLiveData<>(new ArrayList<>());;
        List<Meeting> allMeetingList = allMeetings.getValue();
        allMeetingList.sort(new Comparator<Meeting>() {
            @Override
            public int compare(Meeting meeting, Meeting t1) {
                int comparison;
                comparison = meeting.getLocation().getRoomNumber()-t1.getLocation().getRoomNumber();
                return comparison;
            }
        });
        sortedMeeting.setValue(allMeetingList);
        return sortedMeeting;
    }
    public MutableLiveData<List<Meeting>> getSortedMeetingsTime(){
        MutableLiveData<List<Meeting>> sortedMeeting = new MutableLiveData<>(new ArrayList<>());;
        List<Meeting> allMeetingList = allMeetings.getValue();
        allMeetingList.sort(new Comparator<Meeting>() {
            @Override
            public int compare(Meeting meeting, Meeting t1) {
                int comparison;
                comparison = meeting.getDate().toString().compareTo(t1.getDate().toString());
                return comparison;
            }
        });
        sortedMeeting.setValue(allMeetingList);
        return sortedMeeting;
    }
    public MutableLiveData<List<Meeting>> getResetSorting(){
        MutableLiveData<List<Meeting>> sortedMeeting = new MutableLiveData<>(new ArrayList<>());;
        List<Meeting> allMeetingList = allMeetings.getValue();
        allMeetingList.sort(new Comparator<Meeting>() {
            @Override
            public int compare(Meeting meeting, Meeting t1) {
                int comparison;
                comparison = (int)(meeting.getId()-t1.getId());
                return comparison;
            }
        });
        sortedMeeting.setValue(allMeetingList);
        return sortedMeeting;
    }
    public MutableLiveData<List<Room>> getAllRooms() {
        return allRooms;
    }
    public String[] getRoomNumbers(){
        List<Room> rooms = allRooms.getValue();
        String[] roomNumbers = new String[rooms.size()];
        for(int i = 0; i < rooms.size(); i++){
            roomNumbers[i]=Integer.toString(rooms.get(i).getRoomNumber());
        }
        return roomNumbers;
    }
    public void deleteMeeting(long meetingId) {

        myDatabase.deleteMeeting(meetingId);
        allMeetings.setValue(myDatabase.getAllMeetings());
    }
    public void addMeeting(LocalDateTime date, Room location, String subject, List<String> participants){
        myDatabase.addMeeting(date,location, subject, participants);
        allMeetings.setValue(myDatabase.getAllMeetings());

    }
    public void addRoom(int roomNumber, int maxSize){
       myDatabase.addRoom(roomNumber,maxSize);
       allRooms.setValue(myDatabase.getAllRooms());
    }

}
