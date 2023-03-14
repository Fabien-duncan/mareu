package com.example.mareu.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.repositories.MeetingRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
/**
 * ViewModel fo the add meeting activity.
 * Links the Meeting repository with the add meeting activity.
 */
public class AddMeetingViewModel extends ViewModel {
    private MeetingRepository mMeetingRepository;


    public AddMeetingViewModel(MeetingRepository meetingRepository) {
        mMeetingRepository = meetingRepository;
    }

    public LiveData<List<Meeting>> getAllMeetings(){
        return mMeetingRepository.getAllMeetings();
    }
    public LiveData<List<Room>> getAllRooms(){
        return mMeetingRepository.getAllRooms();
    }
    /**
     * Used to create a new Meeting with:
     * @param date of new meeting
     * @param location of meeting
     * @param subject of meeting
     * @param participants List of participants as a string
     */
    public void onAddButtonClicked(LocalDateTime date, int location, String subject, String participants){
        List<Room> rooms = mMeetingRepository.getAllRooms().getValue();
        List<String> participantsList = Arrays.asList(participants.split(",", -1));
        mMeetingRepository.addMeeting(date,rooms.get(location),subject,participantsList);
    }

    /**
     * @return array of Strings containing the room numbers
     */
    public String[] getRoomNumbers(){
        return mMeetingRepository.getRoomNumbers();
    }
}
