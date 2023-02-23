package com.example.mareu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mareu.adapters.MeetingAdapter;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.models.Meeting;
import com.example.mareu.repositories.MeetingRespository;
import com.example.mareu.viewmodels.MainActivityViewModel;

import java.security.Provider;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MainActivityViewModel mMainActivityViewModel;
    private MeetingAdapter mMeetingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.meetings_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMeetingAdapter = new MeetingAdapter();
        mRecyclerView.setAdapter(mMeetingAdapter);

        //mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        //mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mMainActivityViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(MainActivityViewModel.class);
        mMainActivityViewModel.init();
        mMainActivityViewModel.getAllMeetings().observe(this, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                // update RecyclerView
                mMeetingAdapter.setMeetings(meetings);
            }
        });
    }
}