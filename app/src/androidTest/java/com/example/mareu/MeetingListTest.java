package com.example.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.example.mareu.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

//import android.support.test.espresso.contrib.RecyclerViewActions;

import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.collection.LongSparseArray;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.models.Room;
import com.example.mareu.repositories.MeetingRepository;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MeetingListTest {
    //private static int ITEMS_COUNT = 12;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);
    @Before
    public void setUp() throws Exception{
        //meetingRepository.getAllMeetings().getValue().clear();
        //generateSomeMeetings();
    }
    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(1)));
    }
    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem(){
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(withItemCount(9));
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(1,new DeleteViewAction()));
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(withItemCount(8));

    }
    @Test
    public void createMeeting_shouldAddItem(){
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(withItemCount(9));
        onView(withId(R.id.main_fab_add)).perform(click());
        onView(withId(R.id.addMeeting_perticipants_textInput)).perform(typeText("person1@gmail.com, person2@gmail.com"));
        onView(withId(R.id.addMeeting_subject_textInput)).perform(typeText("testMeeting"));

        onView(withId(R.id.addMeeting_timePicker_btn)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(20, 30));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.addMeeting_datePicker_btn)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2023, 3, 20));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.addMeeting_roomSelection_Spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("109"))).perform(click());

        onView(withId(R.id.addMeeting_add_button)).perform(click());
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(withItemCount(10));
    }
}
