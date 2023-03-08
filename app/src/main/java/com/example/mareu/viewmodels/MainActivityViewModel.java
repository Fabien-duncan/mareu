package com.example.mareu.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.repositories.MeetingRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
    public String createDateTimeString(String type,int year, int month, int day, int hours, int minutes)
    {
        String dateTime = "";
        year = year%100;
        switch (type) {
            case "year":
                dateTime += String.format(Locale.getDefault(),"%02d", year);
                break;
            case "month":
                dateTime += String.format(Locale.getDefault(),"%02d/%02d",year, month);
                break;
            case "day":
                dateTime += String.format(Locale.getDefault(),"%02d/%02d/%02d",year, month,day);
                break;
            case "hour":
                dateTime += String.format(Locale.getDefault(),"%02d/%02d/%02d %02d",year, month,day,hours);
                break;
            case "minute":
                dateTime += String.format(Locale.getDefault(),"%02d/%02d/%02d %02d:%02d",year, month,day,hours,minutes);
                break;
            default:
                dateTime += String.format(Locale.getDefault(),"%02d/%02d/%02d %02d:%02d",year, month,day,hours,minutes);
        }
        return dateTime;
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
