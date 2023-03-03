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
        //mAllMeetings = mMeetingRepository.getFilteredMeetings(15);
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
    public void filterMeetings(String hours, String minutes) {
        clearFiltersSorts();
        if(minutes.isEmpty()) mAllMeetings.setValue(mMeetingRepository.getFilteredMeetings(Integer.parseInt(hours)).getValue());
        else  mAllMeetings.setValue(mMeetingRepository.getFilteredMeetings(Integer.parseInt(hours), Integer.parseInt(minutes)).getValue());
    }public void filterMeetings(int hours, int minutes) {
        clearFiltersSorts();
        mAllMeetings.setValue(mMeetingRepository.getFilteredMeetings(hours,minutes).getValue());
    }
    public void filterMeetings(int hours) {
        clearFiltersSorts();
        mAllMeetings.setValue(mMeetingRepository.getFilteredMeetings(hours).getValue());
    }
    public void filterMeetings(String roomName) {
        clearFiltersSorts();
        mAllMeetings.setValue(mMeetingRepository.getFilteredMeetings(roomName).getValue());
    }
    public void sortMeetingsRoom(){
        mAllMeetings.setValue(mMeetingRepository.getSortedMeetingsRoom().getValue());
    }
    public void sortMeetingsTime(){
        mAllMeetings.setValue(mMeetingRepository.getSortedMeetingsTime().getValue());
    }
    /*public int timeValidation(String hours, String minutes)
    {
        int status;
        if(hours.isEmpty())return 0;
        else{
            if(Integer.parseInt(hours) < 0)
        }
    }*/
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
