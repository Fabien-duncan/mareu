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
import com.example.mareu.models.Time;

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
    }
    @Test
    public void testGetAllMeetings() {
        assertEquals(4, meetingRepository.getAllMeetings().getValue().size());
    }
    @Test
    public void testDeleteMeeting() {
        long id = meetingRepository.getAllMeetings().getValue().get(0).getId();
        meetingRepository.deleteMeeting(id);
        assertEquals(3, meetingRepository.getAllMeetings().getValue().size());
    }
    @Test
    public void testAddMeeting() {
        meetingRepository.addMeeting(
                new Time(18,0),
                new Room(105,6),
                "recrutement",
                Arrays.asList("person2@gmail.com","person2@gmail.com", "person3@gmail.com")
        );
        assertEquals(5, meetingRepository.getAllMeetings().getValue().size());
    }
}