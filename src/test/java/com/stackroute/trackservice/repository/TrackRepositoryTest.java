package com.stackroute.trackservice.repository;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Target;
import java.util.List;
import java.util.Optional;
//@RunWith annotaion is used to define on which class we are running the test(Spring)
@RunWith(SpringRunner.class)
//Tests annotated with @WebMvcTest will also auto-configure Spring Security
@SpringBootTest
public class TrackRepositoryTest {
//Auto wire them wit trackrepository and track classes
    @Autowired
    private TrackRepository trackRepository;
    private Track track;
    @Before
    public void setUp() throws Exception {
        //Create a track of items
        track=new Track();
        track.setTrackId(5);
        track.setTrackName("Vijay");
        track.setComments("This is the First Track");
    }

    @After
    public void tearDown() throws Exception {
    trackRepository.deleteAll();
    }
    //Test to be written for saving the track,assert them with ID
    @Test
    public void givenATrackShouldReturnSaveTrack(){

        Track fetchTrackId=trackRepository.findById(track.getTrackId()).get();
        Assert.assertEquals(5,fetchTrackId.getTrackId());

    }
//Test to Find the track by its name and to check whether the given value is found in the track
   @Test
    public void givenATrackShouldReturnTrackByTrackName() {
        List<Track> findTrack=trackRepository.findByTrackName("Vijay");
        Assert.assertEquals("Vijay",track.getTrackName());
    }
    //If given track name is not found, it should return the exception
    @Test
    public void givenATrackShouldReturnExceptionIfTrackNameNotFound(){
        Track failureTest=  new Track(52,"ASVR","Anagananaga");
        List<Track> actualTrack=trackRepository.findByTrackName(track.getTrackName());
        Assert.assertNotEquals(failureTest,actualTrack);
        Assert.assertNotSame(failureTest,actualTrack);

    }


}
