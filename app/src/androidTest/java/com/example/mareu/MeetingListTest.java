package com.example.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withInputType;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static com.example.mareu.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.AllOf.allOf;

//import android.support.test.espresso.contrib.RecyclerViewActions;

import android.app.Instrumentation;
import android.app.TimePickerDialog;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import androidx.collection.LongSparseArray;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.models.Room;
import com.example.mareu.repositories.MeetingRepository;

import org.hamcrest.Matcher;
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
    @Test
    public void filterByTime_shouldFilterItemsByYear(){
        onView(withId(R.id.menu_options)).perform(click());
        onView(withText("filtrer")).perform(click());
        onView(withText("par date/heures")).perform(click());

        onView(withId(R.id.filter_date_picker)).perform(PickerActions.setDate(2023, 3, 20));

        onData(allOf()).inAdapterView(withId(R.id.filter_filterType_Spinner)).atPosition(0).perform();

        onView(withId(R.id.filter_time_submit)).perform(click());

        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(withItemCount(4));
    }
    @Test
    public void filterByTime_shouldFilterItemsByMonth(){
        onView(withId(R.id.menu_options)).perform(click());
        onView(withText("filtrer")).perform(click());
        onView(withText("par date/heures")).perform(click());

        onView(withId(R.id.filter_date_picker)).perform(PickerActions.setDate(2023, 3, 20));

        onData(allOf()).inAdapterView(withId(R.id.filter_filterType_Spinner)).atPosition(1).perform();

        onView(withId(R.id.filter_time_submit)).perform(click());

        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(withItemCount(3));
    }
    @Test
    public void filterByTime_shouldFilterItemsByDay(){
        onView(withId(R.id.menu_options)).perform(click());
        onView(withText("filtrer")).perform(click());
        onView(withText("par date/heures")).perform(click());

        onView(withId(R.id.filter_date_picker)).perform(PickerActions.setDate(2022, 2, 12));

        onData(allOf()).inAdapterView(withId(R.id.filter_filterType_Spinner)).atPosition(2).perform();

        onView(withId(R.id.filter_time_submit)).perform(click());
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(withItemCount(5));
    }
    @Test
    public void filterByTime_shouldFilterItemsByHour() throws InterruptedException {
        onView(withId(R.id.menu_options)).perform(click());
        onView(withText("filtrer")).perform(click());
        onView(withText("par date/heures")).perform(click());

        onView(withId(R.id.filter_date_picker)).perform(PickerActions.setDate(2022, 2, 12));
        //onData(allOf()).inAdapterView(withId(R.id.filter_hours_number_picker)).perform();
        //onView(withId(R.id.filter_hours_number_picker)).perform(click()).perform(new GeneralSwipeAction(Swipe.SLOW, GeneralLocation.values(), GeneralLocation.BOTTOM_CENTER, Press.FINGER));
        selectValueNumberPicker(R.id.filter_hours_number_picker, 15);

        onData(allOf()).inAdapterView(withId(R.id.filter_filterType_Spinner)).atPosition(3).perform();
        Thread.sleep(4000);
        onView(withId(R.id.filter_time_submit)).perform(click());
        Thread.sleep(4000);
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(withItemCount(3));
    }
    @Test
    public void filterByTime_shouldFilterItemsByMinutes(){
        onView(withId(R.id.menu_options)).perform(click());
        onView(withText("filtrer")).perform(click());
        onView(withText("par date/heures")).perform(click());

        onView(withId(R.id.filter_date_picker)).perform(PickerActions.setDate(2022, 2, 12));

        selectValueNumberPicker(R.id.filter_hours_number_picker, 15);
        selectValueNumberPicker(R.id.filter_minutes_number_picker, 45);

        onData(allOf()).inAdapterView(withId(R.id.filter_filterType_Spinner)).atPosition(4).perform();
        onView(withId(R.id.filter_time_submit)).perform(click());
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(withItemCount(2));
    }
    @Test
        public void filterSortBy_shouldSortItemsByTime() throws InterruptedException {
        onView(withId(R.id.menu_options)).perform(click());
        onView(withText("trier")).perform(click());
        onView(withText("par heures")).perform(click());

        Thread.sleep(4000);
    }
    private void selectValueNumberPicker(int pickerId, int value)
    {
        onView(withId(pickerId)).perform(new ViewAction() {
            @Override
            public String getDescription() {
                return "Set the value of a NumberPicker";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(NumberPicker.class);
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((NumberPicker)view).setValue(value);
            }
        });
    }


    /*public static void selectNumberPickerValue(int pickerId, int targetValue, ActivityTestRule activityTestRule) {
        final int ROWS_PER_SWIPE = 5;
        NumberPicker numberPicker = (NumberPicker)activityTestRule.getActivity().findViewById(pickerId);
        ViewInteraction viewInteraction = onView(withId(pickerId));

        while (targetValue != numberPicker.getValue()) {
            int delta = Math.abs(targetValue - numberPicker.getValue());
            if (targetValue < numberPicker.getValue()) {
                if (delta >= ROWS_PER_SWIPE) {
                    viewInteraction.perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.TOP_CENTER, GeneralLocation.BOTTOM_CENTER, Press.FINGER));
                } else {
                    viewInteraction.perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.TOP_CENTER, Press.FINGER));
                }
            } else {
                if (delta >= ROWS_PER_SWIPE) {
                    viewInteraction.perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.BOTTOM_CENTER, GeneralLocation.TOP_CENTER, Press.FINGER));
                } else {
                    viewInteraction.perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.BOTTOM_CENTER, Press.FINGER));
                }
            }
            SystemClock.sleep(50);
        }
    }*/
}
