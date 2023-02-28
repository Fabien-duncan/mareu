package com.example.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.mareu.R;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.models.Time;
import com.example.mareu.viewmodels.AddMeetingViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity {

    private TextInputEditText subjectTextInput, participantsTextInput;
    private Button timeButton;
    private MaterialButton addMeetingButton;
    private Spinner roomSelectionSpinner;
    private int hours, minutes;
    private AddMeetingViewModel mAddMeetingViewModel;
    public static Intent navigate(Context context) {
        return new Intent(context, AddMeetingActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_add_meeting);



        mAddMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(AddMeetingViewModel.class);
        timeButton = findViewById(R.id.addMeeting_timePicker_btn);
        roomSelectionSpinner = findViewById(R.id.addMeeting_roomSelection_Spinner);
        addMeetingButton = findViewById(R.id.addMeeting_add_button);
        subjectTextInput = findViewById(R.id.addMeeting_subject_textInput);
        participantsTextInput = findViewById(R.id.addMeeting_perticipants_textInput);

        String[] roomArray = mAddMeetingViewModel.getRoomNumbers();
        ArrayAdapter roomSelectionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, roomArray);
        roomSelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSelectionSpinner.setAdapter(roomSelectionAdapter);

        bindAddButton();
    }
    public void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectHours, int selectMins) {
                hours = selectHours;
                minutes = selectMins;
                timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d",hours, minutes));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hours,minutes,true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
    private void bindAddButton(){
        /*addMeetingButton.setOnClickListener(v -> mAddMeetingViewModel.onAddButtonClicked(
                new Time(hours,minutes),
                roomSelectionSpinner.getSelectedItemPosition(),
                subjectTextInput.getText().toString(),
                participantsTextInput.getText().toString()
        ));*/
        addMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddMeetingViewModel.onAddButtonClicked(
                        new Time(hours,minutes),
                        roomSelectionSpinner.getSelectedItemPosition(),
                        subjectTextInput.getText().toString(),
                        participantsTextInput.getText().toString()
                );
                finish();
            }
        });
    }
}