package com.example.mareu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.mareu.adapters.MeetingAdapter;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.models.Meeting;
import com.example.mareu.models.Room;
import com.example.mareu.models.Time;
import com.example.mareu.repositories.MeetingRespository;
import com.example.mareu.viewmodels.MainActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.Provider;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MeetingAdapter.MeetingClickListener {
    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddMeetingFloatingButton;
    private MainActivityViewModel mMainActivityViewModel;
    private MeetingAdapter mMeetingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.meetings_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAddMeetingFloatingButton = findViewById(R.id.main_fab_add);

        mMeetingAdapter = new MeetingAdapter(this);
        mRecyclerView.setAdapter(mMeetingAdapter);

        //mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        //mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mMainActivityViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(MainActivityViewModel.class);
        mMainActivityViewModel.init();
        mAddMeetingFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mMainActivityViewModel.addMeeting(
                        new Time(18,0),
                        new Room(105,6),
                        "recrutement",
                        Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
                );*/
            }
        });
        mMainActivityViewModel.getAllMeetings().observe(this, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                // update RecyclerView
                mMeetingAdapter.setMeetings(meetings);
            }
        });


    }

    @Override
    public void meetingClick(long id) {

    }

    @Override
    public void removeMeeting(long id) {
        mMainActivityViewModel.deleteMeeting(id);
    }
}