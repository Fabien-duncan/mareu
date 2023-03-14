package com.example.mareu.injection;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.repositories.MeetingRepository;
import com.example.mareu.viewmodels.AddMeetingViewModel;
import com.example.mareu.viewmodels.MainActivityViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * this class is the root of the Dependency Injection for this project.
 * It is a singleton itself, so it is available globally for every ViewModel.
 * Its purpose is to create every ViewModel and inject the correct dependency for each.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

        private MeetingRepository mMeetingRespository;
        private static ViewModelFactory factory;
        //Checks that an instance exists, if not it creates one, insuring only 1 instance exists
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
        /*
        Constructor is private to avoid being able to create instances of this class outside the class
        this helps with insuring this class is a singleton.
        */

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
