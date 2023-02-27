package com.example.mareu.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.models.Time;
import com.example.mareu.repositories.MeetingRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<Meeting>> mAllMeetings;
    private MeetingRepository mMeetingRepository;

    /*/public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mMeetingRespository = new MeetingRespository(application);
        mAllMeetings = mMeetingRespository.getAllMeetings();
    }*/
    public MainActivityViewModel(MeetingRepository meetingRespository) {
        mMeetingRepository = meetingRespository;
        mAllMeetings = mMeetingRepository.getAllMeetings();
    }

    public void init(){
        if(mAllMeetings != null){
            return;
        }
        //mMeetingRespository = MeetingRespository.getInstance();
        mAllMeetings = mMeetingRepository.getAllMeetings();
    }
    public LiveData<List<Meeting>> getAllMeetings(){
        return mAllMeetings;
    }
    public void addMeeting(Time time, Room location, String subject, List<String> participants){
        mMeetingRepository.addMeeting(time, location, subject, participants);
    }
    public void addRoom(int roomNumber, int maxSize){
        mMeetingRepository.addRoom(roomNumber,maxSize);
    }
    public void deleteMeeting(long meetingId){
        mMeetingRepository.deleteMeeting(meetingId);
    }

}
