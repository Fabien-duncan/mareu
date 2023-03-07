package com.example.mareu.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.repositories.MeetingRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
    public void onAddButtonClicked(LocalDateTime date, int location, String subject, String participants){
        List<Room> rooms = mMeetingRepository.getAllRooms().getValue();
        List<String> participantsList = Arrays.asList(participants.split(",", -1));
        mMeetingRepository.addMeeting(date,rooms.get(location),subject,participantsList);
    }
    public String[] getRoomNumbers(){
        return mMeetingRepository.getRoomNumbers();
    }
}
