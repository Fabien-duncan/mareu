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

/**
 * ViewModel fo the main activity.
 * Links the Meeting repository with the main activity.
 */
public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<Meeting>> mAllMeetings;
    private MeetingRepository mMeetingRepository;

    /**
     * constructor method
     * sets up the repository and liveData
     * @param meetingRespository
     */
    public MainActivityViewModel(MeetingRepository meetingRespository) {
        mMeetingRepository = meetingRespository;
        mAllMeetings = mMeetingRepository.getAllMeetings();
    }

    /**
     * initialises the meetings live date if it hasn't been done already
     */
    public void init(){
        if(mAllMeetings != null){
            return;
        }
        mAllMeetings = mMeetingRepository.getAllMeetings();
    }

    /**
     * @return a list of all meetings
     */
    public LiveData<List<Meeting>> getAllMeetings(){
        return mAllMeetings;
    }

    /**
     * Used to filter meetings by date or room.
     * can filter date by: year, month, hour, minute
     * uses the repository method getFilteredMeetings(String type, string value)
     * @param type used for deciding the type of filter:"room", "year", "month", "hour", "minute"
     * @param value what will be used for the filter
     */
    public void filterMeetings(String type, String value){
        clearFiltersSorts();
        mAllMeetings.setValue(mMeetingRepository.getFilteredMeetings(type,value).getValue());
    }

    /**
     * sort meetings by room in ascending order
     * uses the meeting repository method getSortedMeetings(String type)
     */
    public void sortMeetingsRoom(){
        mAllMeetings.setValue(mMeetingRepository.getSortedMeetings("room").getValue());
    }
    /**
     * sort meetings by time/date in ascending order
     * uses the meeting repository method getSortedMeetings(String type)
     */
    public void sortMeetingsTime(){
        mAllMeetings.setValue(mMeetingRepository.getSortedMeetings("time").getValue());
    }

    /**
     * resets the filter in order to display all meetings
     */
    public void clearFiltersSorts(){
        mAllMeetings.setValue(mMeetingRepository.getAllMeetings().getValue());
    }

    /**
     * Formats the date depending on the precision ranging from containing just the year to containing
     * all the details up to the minute e.g yy/mm/dd hh/MM
     * it starts with year in order to help with sorting by date.
     *
     * @param type used to specify the precision
     * @param year
     * @param month
     * @param day
     * @param hours
     * @param minutes
     * @return formatated date string
     */
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

    /**
     * Clear the sorting in order to put it in order to when the meetings were created,
     * hence by id. It uses the getSortedMeetings(String type) method of the repository
     */
    public void clearSorting(){
        mMeetingRepository.getSortedMeetings("reset");
    }

    /**
     * Deletes a meeting by id
     * uses deleteMeeting(long id) method of the repository;
     * @param meetingId
     */
    public void deleteMeeting(long meetingId){
        mMeetingRepository.deleteMeeting(meetingId);
    }

    /**
     * @return array of strings containing the room numbers
     */
    public String[] getRoomNumbers() {
        return mMeetingRepository.getRoomNumbers();
    }
}
