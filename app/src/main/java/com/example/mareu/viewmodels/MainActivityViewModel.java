package com.example.mareu.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.models.Time;
import com.example.mareu.repositories.MeetingRespository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<Meeting>> mAllMeetings;
    private MeetingRespository mMeetingRespository;

    /*/public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mMeetingRespository = new MeetingRespository(application);
        mAllMeetings = mMeetingRespository.getAllMeetings();
    }*/
    public MainActivityViewModel(MeetingRespository meetingRespository) {
        mMeetingRespository = meetingRespository;
        mAllMeetings = mMeetingRespository.getAllMeetings();
    }

    public void init(){
        if(mAllMeetings != null){
            return;
        }
        //mMeetingRespository = MeetingRespository.getInstance();
        mAllMeetings = mMeetingRespository.getAllMeetings();
    }
    public LiveData<List<Meeting>> getAllMeetings(){
        return mAllMeetings;
    }
    public void addMeeting(Time time, Room location, String subject, List<String> participants){
        mMeetingRespository.addMeeting(time, location, subject, participants);
    }
    public void addRoom(int roomNumber, int maxSize){
        mMeetingRespository.addRoom(roomNumber,maxSize);
    }
}
