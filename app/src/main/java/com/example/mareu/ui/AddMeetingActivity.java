package com.example.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.viewmodels.AddMeetingViewModel;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Activity used to create a new meeting
 * Contains:
 * -text fields to enter information
 * -time picker button which opens a TimePickerDialogue
 * -date picker button which opens up a DatePickerDialogue
 * -spinner to select room
 */
public class AddMeetingActivity extends AppCompatActivity {
    private int hours, minutes;
    private int year = 2023, month = 3, day =7;
    private AddMeetingViewModel mAddMeetingViewModel;
    private boolean isTimeSelected = false;
    private boolean isDateSelected = false;
    private ActivityAddMeetingBinding mActivityAddMeetingBinding;
    public static Intent navigate(Context context) {
        return new Intent(context, AddMeetingActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityAddMeetingBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = mActivityAddMeetingBinding.getRoot();
        setContentView(view);

        mAddMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(AddMeetingViewModel.class);

        String[] roomArray = mAddMeetingViewModel.getRoomNumbers();//retrieves rooms for spinner
        ArrayAdapter roomSelectionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, roomArray);

        roomSelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mActivityAddMeetingBinding.addMeetingRoomSelectionSpinner.setAdapter(roomSelectionAdapter);

        bindAddButton();
    }

    /**
     * Used to pop up a time picker dialogue box
     * @param view
     */
    public void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectHours, int selectMins) {
                hours = selectHours;
                minutes = selectMins;
                mActivityAddMeetingBinding.addMeetingTimePickerBtn.setText(String.format(Locale.getDefault(),"%02d:%02d",hours, minutes));
                isTimeSelected = true;
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hours,minutes,true);
        timePickerDialog.setTitle("Selectionner l'heure");
        timePickerDialog.show();
    }
    /**
     * Used to pop up a date picker dialogue box
     * @param view
     */
    public void popDatePicker(View view){

       DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker datePicker, int selectYear, int selectMonth, int selectDay) {
               year = selectYear;
               month = selectMonth + 1;
               day = selectDay;
               mActivityAddMeetingBinding.addMeetingDatePickerBtn.setText(String.format(Locale.getDefault(),"%02d/%02d/%04d", day,month,year));
               isDateSelected = true;
           }
       };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, year,month-1,day);
        datePickerDialog.setTitle("Selectionner la date");
        datePickerDialog.show();
    }

    /**
     * Binds the add button and contains some data validation
     * in order to prevent erroneous data to be used in the
     * creation of a new meeting
     */
    private void bindAddButton(){
        mActivityAddMeetingBinding.addMeetingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = mActivityAddMeetingBinding.addMeetingSubjectTextInput.getText().toString();
                String participants = mActivityAddMeetingBinding.addMeetingPerticipantsTextInput.getText().toString();
                //Data Validation
                if(subject.isEmpty()||participants.isEmpty()||!isTimeSelected||!isDateSelected) Toast.makeText(getApplicationContext(), "formulaire pas remplis", Toast.LENGTH_LONG).show();
                else {
                    String emailValidationMessage = mAddMeetingViewModel.onAddButtonClicked(
                            LocalDateTime.of(year,month,day,hours, minutes),
                            mActivityAddMeetingBinding.addMeetingRoomSelectionSpinner.getSelectedItemPosition(),
                            subject,
                            participants
                    );
                    if(emailValidationMessage.equals("valid"))finish();
                    else {
                        Toast.makeText(getApplicationContext(), emailValidationMessage, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}