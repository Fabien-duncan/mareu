package com.example.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.example.mareu.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.mareu.data.MyDatabase;
import com.example.mareu.models.Meeting;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Instrumental tests for the Mareu app. This test that the UI works properly and the the interaction behave appropriately.
 */
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
     * We ensure that our recyclerview is displaying at least one item
     */
    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(matches(hasMinimumChildCount(1)));
    }
    /**
     * When we delete a Meeting, the Meeting is shown no more
     */
    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem(){
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(withItemCount(9));
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(1,new DeleteViewAction()));
        onView(allOf(withId(R.id.meetings_rv), isDisplayed())).check(withItemCount(8));

    }

    /**
     * When we create a a Meeting there should be an extra Meeting in the list
     */
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

    /**
     * When we filter meetings by year it should only display the meetings of that year only
     */
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
    /**
     * When we filter meetings by Month it should only display the meetings of that a specific year and that month only
     */
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
    /**
     * When we filter meetings by Day it should only display the meetings of that a specific year, month and that day only
     */
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
    /**
     * When we filter meetings by Hour it should only display the meetings of that a specific year, month, day and that Hour only
     */
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
    /**
     * When we filter meetings by Minutes it should only display the meetings of that a specific year, month, day, hour and that minute only
     */
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
    /**
     * When we sort the Meetings by Time the list of meetings should be ordered by date in ascending order
     */
    @Test
        public void filterSortBy_shouldSortItemsByTime(){
        onView(withId(R.id.menu_options)).perform(click());
        onView(withText("trier")).perform(click());
        onView(withText("par heures")).perform(click());

        List<String> sortedMeetings = getsortedMeetingList("time");
        for(int i =0; i < sortedMeetings.size()-1; i++) {
            onView(new RecyclerViewMatcher(R.id.meetings_rv)
                    .atPositionOnView(i, R.id.meetings_item_tv_details))
                    .check(matches(withText(sortedMeetings.get(i))));
        }
    }
    /**
     * When we sort the Meetings by Room the list of meetings should be ordered by Rooms in ascending order
     */
    @Test
    public void filterSortBy_shouldSortItemsByRoom(){
        onView(withId(R.id.menu_options)).perform(click());
        onView(withText("trier")).perform(click());
        onView(withText("par salles")).perform(click());


        List<String> sortedMeetings = getsortedMeetingList("room");
        for(int i =0; i < sortedMeetings.size()-1; i++) {
            onView(new RecyclerViewMatcher(R.id.meetings_rv)
                    .atPositionOnView(i, R.id.meetings_item_tv_details))
                    .check(matches(withText(sortedMeetings.get(i))));
        }
    }
    //used to input values in a number picker using the id and a value
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
    //used to return a list of sorted meetings. This method is used to check of the recyclerview items are sorted correctly.
    private List<String> getsortedMeetingList(String type){
        MyDatabase database = new MyDatabase();
        List<Meeting> meetings = database.getAllMeetings();
        meetings.sort(new Comparator<Meeting>() {
            @Override
            public int compare(Meeting meeting, Meeting t1) {
                int comparison;
                switch (type) {
                    case "time":
                        comparison = meeting.getDate().toString().compareTo(t1.getDate().toString());
                        break;
                    case "room":
                        comparison = meeting.getLocation().getRoomNumber()-t1.getLocation().getRoomNumber();
                        break;
                    default:
                        comparison = (int)(meeting.getId()-t1.getId());


                }
                return comparison;
            }
        });
        List<String> outputList =new ArrayList<>();
        for(int i =0; i < meetings.size(); i++){
            outputList.add(meetings.get(i).detailsToString());
        }
        return outputList;
    }
}
