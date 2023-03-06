package com.example.mareu.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.models.Time;
import com.example.mareu.repositories.MeetingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddMeetingViewModel extends ViewModel {
    //private MutableLiveData<List<Meeting>> mAllMeetings;
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
        /*for(int i = 0; i < rooms.size(); i++){
            if(Integer.parseInt(location) == rooms.get(i).getRoomNumber()){
                mMeetingRepository.addMeeting(time,rooms.get(i),subject,participants);
            }
        }*/
        mMeetingRepository.addMeeting(date,rooms.get(location),subject,participantsList);
    }
    public String[] getRoomNumbers(){
        return mMeetingRepository.getRoomNumbers();
    }
}
