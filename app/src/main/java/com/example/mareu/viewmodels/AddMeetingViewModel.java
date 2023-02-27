package com.example.mareu.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.models.Time;
import com.example.mareu.repositories.MeetingRepository;

import java.util.List;

public class AddMeetingViewModel extends ViewModel {
    //private MutableLiveData<List<Meeting>> mAllMeetings;
    private MeetingRepository mMeetingRepository;


    public AddMeetingViewModel(MeetingRepository meetingRepository) {
        mMeetingRepository = meetingRepository;
    }

    public void onAddButtonClicked(Time time, Room location, String subject, List<String> participants){
        mMeetingRepository.addMeeting(time,location,subject,participants);
    }
}
