package com.example.mareu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.databinding.RoomFilterDialogBinding;
import com.example.mareu.databinding.TimeFilterDialogBinding;
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
    private MainActivityViewModel mMainActivityViewModel;
    private MeetingAdapter mMeetingAdapter;
    private AlertDialog mTimePickerAlertDialog;
    private AlertDialog mRoomPickerAlertDialog;
    private ActivityMainBinding mActivityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mActivityMainBinding.getRoot();
        setContentView(view);

        mActivityMainBinding.meetingsRv.setLayoutManager(new LinearLayoutManager(this));


        mMeetingAdapter = new MeetingAdapter(this);
        mActivityMainBinding.meetingsRv.setAdapter(mMeetingAdapter);

        mMainActivityViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(MainActivityViewModel.class);
        mMainActivityViewModel.init();
        mActivityMainBinding.mainFabAdd.setOnClickListener(v -> startActivity(AddMeetingActivity.navigate(this)));

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
     * Feature not created yet. Would be used to open a activity containing the details of a meeting
     * @param id
     */
    @Override
    public void meetingClick(long id) {

    }

    /**
     * this method is use to create and access a time picker in order to be able to filter
     * meetings by time and/or date. A date picker, 2 number pickers and a spinner are created.
     * The Number pickers are used for selecting a time.
     * The spinner is used for selecting the precision of the filter.
     */
    public void createTimeFilterPicker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        TimeFilterDialogBinding timeFilterDialogBinding = TimeFilterDialogBinding.inflate(li);
        builder.setView(timeFilterDialogBinding.getRoot());

        String[] filterTypes = {"année","mois","jour", "heure", "minute"}; //precison of filter
        ArrayAdapter filterTypesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, filterTypes);
        filterTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeFilterDialogBinding.filterFilterTypeSpinner.setAdapter(filterTypesAdapter);

        String[] filterTypesTranslator = {"year","month","day", "hour", "minute"};//parallel array of filter type used to translate into French

        timeFilterDialogBinding.filterHoursNumberPicker.setMinValue(0);
        timeFilterDialogBinding.filterHoursNumberPicker.setMaxValue(23);
        timeFilterDialogBinding.filterMinutesNumberPicker.setMaxValue(0);
        timeFilterDialogBinding.filterMinutesNumberPicker.setMaxValue(59);

        timeFilterDialogBinding.filterTimeSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = filterTypesTranslator[timeFilterDialogBinding.filterFilterTypeSpinner.getSelectedItemPosition()];
                mMainActivityViewModel.filterMeetings(type, mMainActivityViewModel.createDateTimeString(
                        type,
                        timeFilterDialogBinding.filterDatePicker.getYear(),
                        timeFilterDialogBinding.filterDatePicker.getMonth()+1,
                        timeFilterDialogBinding.filterDatePicker.getDayOfMonth(),
                        timeFilterDialogBinding.filterHoursNumberPicker.getValue(),
                        timeFilterDialogBinding.filterMinutesNumberPicker.getValue()));
                mTimePickerAlertDialog.dismiss();
            }
        });
        mTimePickerAlertDialog = builder.create();
    }

    /**
     * used to create an AlerDialogue for selecting the room.
     * Contains a spinner to select a room.
     */
    public void createRoomFilterPicker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choisir la Salle a filtrer");
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        RoomFilterDialogBinding roomFilterDialogBinding = RoomFilterDialogBinding.inflate(li);
        builder.setView(roomFilterDialogBinding.getRoot());

        String[] roomArray = mMainActivityViewModel.getRoomNumbers();//retrieves room number to use for the spinner
        ArrayAdapter roomSelectionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, roomArray);
        roomSelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomFilterDialogBinding.filterRoomSpinner.setAdapter(roomSelectionAdapter);
        roomFilterDialogBinding.filterRoomSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "searching for Room " + roomArray[roomFilterDialogBinding.filterRoomSpinner.getSelectedItemPosition()], Toast.LENGTH_SHORT).show();
                mMainActivityViewModel.filterMeetings("room",roomArray[roomFilterDialogBinding.filterRoomSpinner.getSelectedItemPosition()]);
                mRoomPickerAlertDialog.dismiss();
            }
        });
        mRoomPickerAlertDialog = builder.create();
    }

    /**
     * Overrides the method from the MeetingClickListener interface inside the MeetingAdapter class
     * Deletes a Meeting object by ID by calling the deleteMeeting(long id) method of the viewModel
     * @param id of meeting to delete
     */
    @Override
    public void removeMeeting(long id) {
        mMainActivityViewModel.deleteMeeting(id);
    }
}