package com.example.mareu.injection;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.repositories.MeetingRepository;
import com.example.mareu.viewmodels.AddMeetingViewModel;
import com.example.mareu.viewmodels.MainActivityViewModel;

import org.jetbrains.annotations.NotNull;

public class ViewModelFactory implements ViewModelProvider.Factory {

        private MeetingRepository mMeetingRespository;
        private static ViewModelFactory factory;

        public static ViewModelFactory getInstance(Context context) {
            if (factory == null) {
                synchronized (ViewModelFactory.class) {
                    if (factory == null) {
                        factory = new ViewModelFactory(context);
                    }
                }
            }
            return factory;
        }

        private ViewModelFactory(Context context) {
            this.mMeetingRespository = new MeetingRepository();
        }

        @Override
        @NotNull
        public <T extends ViewModel>  T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
                return (T) new MainActivityViewModel(mMeetingRespository);
            }else if (modelClass.isAssignableFrom(AddMeetingViewModel.class)) {
                return (T) new AddMeetingViewModel(mMeetingRespository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
}
