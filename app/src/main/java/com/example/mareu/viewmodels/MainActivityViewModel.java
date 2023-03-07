package com.example.mareu.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.repositories.MeetingRepository;

import java.time.LocalDateTime;
import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<Meeting>> mAllMeetings;
    private MeetingRepository mMeetingRepository;

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
        //mAllMeetings = mMeetingRepository.getFilteredMeetings(15);
    }
    public LiveData<List<Meeting>> getAllMeetings(){
        return mAllMeetings;
    }
    public void addMeeting(LocalDateTime date, Room location, String subject, List<String> participants){
        mMeetingRepository.addMeeting(date, location, subject, participants);
    }
    public void addRoom(int roomNumber, int maxSize){
        mMeetingRepository.addRoom(roomNumber,maxSize);
    }
    public void filterMeetings(String type, String value){
        clearFiltersSorts();
        mAllMeetings.setValue(mMeetingRepository.getFilteredMeetings(type,value).getValue());
    }
    public void sortMeetingsRoom(){
        mAllMeetings.setValue(mMeetingRepository.getSortedMeetings("room").getValue());
    }
    public void sortMeetingsTime(){
        mAllMeetings.setValue(mMeetingRepository.getSortedMeetings("time").getValue());
    }
    public void clearFiltersSorts(){
        mAllMeetings.setValue(mMeetingRepository.getAllMeetings().getValue());
    }
    public void clearSorting(){
        mMeetingRepository.getResetSorting();
    }
    public void deleteMeeting(long meetingId){
        mMeetingRepository.deleteMeeting(meetingId);
    }

    public String[] getRoomNumbers() {
        return mMeetingRepository.getRoomNumbers();
    }
}
