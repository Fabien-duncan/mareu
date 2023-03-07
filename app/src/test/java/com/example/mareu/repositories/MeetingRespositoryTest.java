package com.example.mareu.repositories;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.mareu.models.Room;

import java.time.LocalDateTime;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class MeetingRespositoryTest{

    @Spy
    private MeetingRepository meetingRepository;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        //meetingRepository = new MeetingRespository();
        meetingRepository.getAllMeetings().getValue().clear();
        generateSomeMeetings();
    }


    @Test
    public void testGetAllMeetings() {
        assertEquals(7, meetingRepository.getAllMeetings().getValue().size());
    }
    @Test
    public void testDeleteMeeting() {
        long id = meetingRepository.getAllMeetings().getValue().get(0).getId();
        meetingRepository.deleteMeeting(id);
        assertEquals(6, meetingRepository.getAllMeetings().getValue().size());
    }
    @Test
    public void testAddMeeting() {
        meetingRepository.addMeeting(
                LocalDateTime.of(2022,2,12,18,0),
                new Room(105,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        assertEquals(5, meetingRepository.getAllMeetings().getValue().size());
    }
    @Test
    public void testFilterByHour(){
        meetingRepository.addMeeting(
                LocalDateTime.of(2022,2,12,15,0),
                new Room(105,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        assertEquals(3,meetingRepository.getFilteredMeetings("hour", "22/02/12 15").getValue().size());
    }
    @Test
    public void testFilterByHour_twice(){
        meetingRepository.addMeeting(
                LocalDateTime.of(2022,2,12,15,0),
                new Room(105,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        assertEquals(3,meetingRepository.getFilteredMeetings("hour", "22/02/12 15").getValue().size());
        assertEquals(1,meetingRepository.getFilteredMeetings("hour", "22/02/12 14").getValue().size());
    }
    @Test
    public void testFilterByMinutes(){
        meetingRepository.addMeeting(
                LocalDateTime.of(2022,2,12,15,45),
                new Room(105,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        assertEquals(2,meetingRepository.getFilteredMeetings("minute", "22/02/12 15:45").getValue().size());
    }
    @Test
    public void testFilterByDay(){
        assertEquals(3,meetingRepository.getFilteredMeetings("day", "22/02/12").getValue().size());
    }
    @Test
    public void testFilterByDay_twice(){
        assertEquals(3,meetingRepository.getFilteredMeetings("day", "22/02/12").getValue().size());
        assertEquals(1,meetingRepository.getFilteredMeetings("day", "23/03/27").getValue().size());
    }
    @Test
    public void testFilterByMonth(){
        assertEquals(2,meetingRepository.getFilteredMeetings("month", "23/03").getValue().size());
    }
    @Test
    public void testFilterByMonth_twice(){
        assertEquals(3,meetingRepository.getFilteredMeetings("month", "22/02").getValue().size());
        assertEquals(2,meetingRepository.getFilteredMeetings("month", "23/03").getValue().size());
    }
    @Test
    public void testFilterByYear_twice(){
        assertEquals(4,meetingRepository.getFilteredMeetings("year", "23").getValue().size());
        assertEquals(3,meetingRepository.getFilteredMeetings("year", "22").getValue().size());
    }
    @Test
    public void testFilterByRoom(){
        meetingRepository.addMeeting(
                LocalDateTime.of(2022,2,12,15,45),
                new Room(105,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        assertEquals(2,meetingRepository.getFilteredMeetings("room","105").getValue().size());
    }
    private void generateSomeMeetings() {
        meetingRepository.addMeeting(
                LocalDateTime.of(2022,2,12,15,45),
                new Room(105,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        meetingRepository.addMeeting(
                LocalDateTime.of(2022,2,12,15,0),
                new Room(101,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        meetingRepository.addMeeting(
                LocalDateTime.of(2022,2,12,14,0),
                new Room(106,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        meetingRepository.addMeeting(
                LocalDateTime.of(2023,2,12,15,0),
                new Room(109,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );

        meetingRepository.addMeeting(
                LocalDateTime.of(2023,3,24,15,0),
                new Room(109,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        meetingRepository.addMeeting(
                LocalDateTime.of(2023,3,27,15,0),
                new Room(109,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        meetingRepository.addMeeting(
                LocalDateTime.of(2021,3,27,15,0),
                new Room(109,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        meetingRepository.addMeeting(
                LocalDateTime.of(2023,2,25,15,0),
                new Room(109,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
    }

}