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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.adapters.MeetingAdapter;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.models.Meeting;
import com.example.mareu.ui.AddMeetingActivity;
import com.example.mareu.viewmodels.MainActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

/**
 * Main activity is responsible for home page of the application. It implements the MeetingClickListener interface inside the
 * Meeting Adpater class, in order to handle clicking on items of the recycler view.
 *It contains:
 * -a recycler view with a a list of meetings
 * -buttons to delete each meeting
 * -a button to create a new meeting
 * -the ability to filter or sort meetings by time or room
 */
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

    /**
     * @param item is the item in a menu
     * @return if any item has been selected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cancel_filters:
                mMainActivityViewModel.clearSorting();
                mMainActivityViewModel.clearFiltersSorts();
                return true;
            case R.id.menu_filter_time:
                mTimePickerAlertDialog.show();
                return true;
            case R.id.menu_filter_room:
                mRoomPickerAlertDialog.show();
                return true;
            case R.id.menu_sort_by_time:
                mMainActivityViewModel.sortMeetingsTime();
                return true;
            case R.id.menu_sort_by_room:
                mMainActivityViewModel.sortMeetingsRoom();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Feature no created yet. Would be used to open a activity containin the details of a meeting
     * @param id
     */
    @Override
    public void meetingClick(long id) {

    }

    /**
     * this method is use to create and access a time picker in order top be able to filter
     * meetings by time and/or date. A date picker, 2 number pickers and a spinner are created.
     * The Number pickers are used for selecting a time.
     * The spinner is used for selecting the precision of the filter.
     */
    public void createTimeFilterPicker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.time_filter_dialog, null);

        //Binding
        Button submitTimeFilter = view.findViewById(R.id.filter_time_submit);
        NumberPicker hoursNumberPicker = view.findViewById(R.id.filter_hours_number_picker);
        NumberPicker minutesNumberPicker = view.findViewById(R.id.filter_minutes_number_picker);
        DatePicker datePicker = view.findViewById(R.id.filter_date_picker);
        Spinner filterTypeSelectionSpinner = view.findViewById(R.id.filter_filterType_Spinner);

        String[] filterTypes = {"année","mois","jour", "heure", "minute"}; //precison of filter
        ArrayAdapter filterTypesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, filterTypes);
        filterTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterTypeSelectionSpinner.setAdapter(filterTypesAdapter);

        String[] filterTypesTranslator = {"year","month","day", "hour", "minute"};//parallel array of filter type used to translate into French

        hoursNumberPicker.setMinValue(0);
        hoursNumberPicker.setMaxValue(23);
        minutesNumberPicker.setMaxValue(0);
        minutesNumberPicker.setMaxValue(59);

        submitTimeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = filterTypesTranslator[filterTypeSelectionSpinner.getSelectedItemPosition()];
                mMainActivityViewModel.filterMeetings(type, mMainActivityViewModel.createDateTimeString(
                        type,
                        datePicker.getYear(),
                        datePicker.getMonth()+1,
                        datePicker.getDayOfMonth(),
                        hoursNumberPicker.getValue(),
                        minutesNumberPicker.getValue()));
                mTimePickerAlertDialog.dismiss();
            }
        });
        builder.setView(view);
        mTimePickerAlertDialog = builder.create();
    }

    /**
     * used to create a AlerDialogue for selecting the room.
     * Contains a spinner to select a room.
     */
    public void createRoomFilterPicker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choisir la Salle a filtrer");
        View view = getLayoutInflater().inflate(R.layout.room_filter_dialog, null);
        Button submitRoomFilter = view.findViewById(R.id.filter_room_submit);
        Spinner roomSelectionSpinner= view.findViewById(R.id.filter_room_spinner);
        String[] roomArray = mMainActivityViewModel.getRoomNumbers();//retrieves room number to use for the spinner
        ArrayAdapter roomSelectionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, roomArray);
        roomSelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSelectionSpinner.setAdapter(roomSelectionAdapter);
        submitRoomFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "searching for Room " + roomArray[roomSelectionSpinner.getSelectedItemPosition()], Toast.LENGTH_SHORT).show();
                mMainActivityViewModel.filterMeetings("room",roomArray[roomSelectionSpinner.getSelectedItemPosition()]);
                mRoomPickerAlertDialog.dismiss();
            }
        });
        builder.setView(view);
        mRoomPickerAlertDialog = builder.create();
    }

    /**
     * Overides the method from the MeetingClickListener interface inside the MeetingAdapter class
     * Deletes a Meeting obejct by ID by calling the deleteMeeting(long id) method of the viewModel
     * @param id of meeting to delete
     */
    @Override
    public void removeMeeting(long id) {
        mMainActivityViewModel.deleteMeeting(id);
    }
}