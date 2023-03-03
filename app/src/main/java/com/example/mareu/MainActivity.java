package com.example.mareu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mareu.adapters.MeetingAdapter;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.models.Meeting;
import com.example.mareu.ui.AddMeetingActivity;
import com.example.mareu.viewmodels.MainActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MeetingAdapter.MeetingClickListener {
    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddMeetingFloatingButton;
    private MainActivityViewModel mMainActivityViewModel;
    private MeetingAdapter mMeetingAdapter;

    private AlertDialog mTimePickerAlertDialog;
    private AlertDialog mRoomPickerAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.meetings_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAddMeetingFloatingButton = findViewById(R.id.main_fab_add);

        mMeetingAdapter = new MeetingAdapter(this);
        mRecyclerView.setAdapter(mMeetingAdapter);

        mMainActivityViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(MainActivityViewModel.class);
        mMainActivityViewModel.init();
        mAddMeetingFloatingButton.setOnClickListener(v -> startActivity(AddMeetingActivity.navigate(this)));

        createTimeFilterPicker();
        createRoomFilterPicker();
        mMainActivityViewModel.getAllMeetings().observe(this, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                // update RecyclerView
                mMeetingAdapter.setMeetings(meetings);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cancel_filters:
                mMainActivityViewModel.clearSorting();
                mMainActivityViewModel.clearFiltersSorts();
                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_filter_time:
                mTimePickerAlertDialog.show();
                //popTimePicker();
                Toast.makeText(MainActivity.this, "filter by time", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_filter_room:
                Toast.makeText(MainActivity.this, "filter by room", Toast.LENGTH_SHORT).show();
                mRoomPickerAlertDialog.show();
                return true;
            case R.id.menu_sort_by_time:
                Toast.makeText(MainActivity.this, "sort by time", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_sort_by_room:
                Toast.makeText(MainActivity.this, "sort by room", Toast.LENGTH_SHORT).show();
                mMainActivityViewModel.sortMeetingsRoom();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void meetingClick(long id) {

    }
    public void createTimeFilterPicker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choisir l'heure a filtrer");
        View view = getLayoutInflater().inflate(R.layout.time_filter_dialog, null);
        EditText eHours, eMinutes;
        //eHours = view.findViewById(R.id.filter_time_hours);
        //eMinutes = view.findViewById(R.id.filter_time_minutes);
        Button submitTimeFilter = view.findViewById(R.id.filter_time_submit);
        NumberPicker hoursNumberPicker = view.findViewById(R.id.filter_hours_number_picker);
        NumberPicker minutesNumberPicker = view.findViewById(R.id.filter_minutes_number_picker);
        CheckBox minutesCheckBox = view.findViewById(R.id.filter_minutes_check_box);
        hoursNumberPicker.setMinValue(0);
        hoursNumberPicker.setMaxValue(23);
        minutesNumberPicker.setMaxValue(0);
        minutesNumberPicker.setMaxValue(59);
        submitTimeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "searching for " + hoursNumberPicker.getValue() + ":" + minutesNumberPicker.getValue(), Toast.LENGTH_SHORT).show();

                //mMainActivityViewModel.filterMeetings(eHours.getText().toString(),eMinutes.getText().toString());
                if(minutesCheckBox.isChecked())mMainActivityViewModel.filterMeetings(hoursNumberPicker.getValue(),minutesNumberPicker.getValue());
                else mMainActivityViewModel.filterMeetings(hoursNumberPicker.getValue());
                mTimePickerAlertDialog.dismiss();
            }
        });
        builder.setView(view);
        mTimePickerAlertDialog = builder.create();
    }
    public void createRoomFilterPicker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choisir la Salle a filtrer");
        View view = getLayoutInflater().inflate(R.layout.room_filter_dialog, null);
        Button submitRoomFilter = view.findViewById(R.id.filter_room_submit);
        Spinner roomSelectionSpinner= view.findViewById(R.id.filter_room_spinner);
        String[] roomArray = mMainActivityViewModel.getRoomNumbers();
        ArrayAdapter roomSelectionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, roomArray);
        roomSelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSelectionSpinner.setAdapter(roomSelectionAdapter);
        submitRoomFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "searching for Room " + roomArray[roomSelectionSpinner.getSelectedItemPosition()], Toast.LENGTH_SHORT).show();
                mMainActivityViewModel.filterMeetings(roomArray[roomSelectionSpinner.getSelectedItemPosition()]);
                mRoomPickerAlertDialog.dismiss();
            }
        });
        builder.setView(view);
        mRoomPickerAlertDialog = builder.create();
    }


    @Override
    public void removeMeeting(long id) {
        mMainActivityViewModel.deleteMeeting(id);
    }
    /*public void onMinutesCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.filter_minutes_check_box:
                if (checked)

                else
                // Remove the meat
                break;
        }
    }*/
}