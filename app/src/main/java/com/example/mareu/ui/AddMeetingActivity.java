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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.viewmodels.AddMeetingViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

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

    private TextInputEditText subjectTextInput, participantsTextInput;
    private Button timeButton;
    private Button dateButton;
    private MaterialButton addMeetingButton;
    private Spinner roomSelectionSpinner;
    private int hours, minutes;
    private int year = 2023, month = 3, day =7;
    private AddMeetingViewModel mAddMeetingViewModel;
    private boolean isTimeSelected = false;
    private boolean isDateSelected = false;
    public static Intent navigate(Context context) {
        return new Intent(context, AddMeetingActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_add_meeting);

        mAddMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(AddMeetingViewModel.class);
        //Binding
        timeButton = findViewById(R.id.addMeeting_timePicker_btn);
        dateButton = findViewById(R.id.addMeeting_datePicker_btn);
        roomSelectionSpinner = findViewById(R.id.addMeeting_roomSelection_Spinner);
        addMeetingButton = findViewById(R.id.addMeeting_add_button);
        subjectTextInput = findViewById(R.id.addMeeting_subject_textInput);
        participantsTextInput = findViewById(R.id.addMeeting_perticipants_textInput);

        String[] roomArray = mAddMeetingViewModel.getRoomNumbers();//retrieves rooms for spinner
        ArrayAdapter roomSelectionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, roomArray);

        roomSelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSelectionSpinner.setAdapter(roomSelectionAdapter);

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
                timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d",hours, minutes));
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
               dateButton.setText(String.format(Locale.getDefault(),"%02d/%02d/%04d", day,month,year));
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
        addMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = subjectTextInput.getText().toString();
                String participants = participantsTextInput.getText().toString();
                //checks if fields are not empty
                if(subject.isEmpty()||participants.isEmpty()||!isTimeSelected||!isDateSelected) Toast.makeText(getApplicationContext(), "missing fiels", Toast.LENGTH_LONG).show();
                else {
                        mAddMeetingViewModel.onAddButtonClicked(
                            LocalDateTime.of(year,month,day,hours, minutes),
                            roomSelectionSpinner.getSelectedItemPosition(),
                            subject,
                            participants
                        );
                    finish();
                }
            }
        });
    }
}