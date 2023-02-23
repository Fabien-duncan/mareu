package com.example.mareu.injection;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.repositories.MeetingRespository;
import com.example.mareu.viewmodels.MainActivityViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

        private MeetingRespository mMeetingRespository;
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
            this.mMeetingRespository = new MeetingRespository();
        }

        @Override
        @NotNull
        public <T extends ViewModel>  T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
                return (T) new MainActivityViewModel(mMeetingRespository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
}
