package com.example.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.mareu.R;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.viewmodels.AddMeetingViewModel;
import com.example.mareu.viewmodels.MainActivityViewModel;

import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity {

    private Button timeButton;
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

    }
}