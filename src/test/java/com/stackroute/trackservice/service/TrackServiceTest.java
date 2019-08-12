package com.stackroute.trackservice.service;
import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackIdAlreadyExistsException;
import com.stackroute.trackservice.exceptions.TrackNotFoundException;
import com.stackroute.trackservice.repository.TrackRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
//Create a trackService Test class and check for methods implemented in trackserviceImpl
public class TrackServiceTest {
    //Track object should be created as private
    private Track track;
    //Inject mock into track repository from track SErvice Impl
    @Mock
    TrackRepository trackRepository;
    @InjectMocks
    TrackServiceImpl trackService;
    List<Track> list= null;


    @Before
    public void setUp() throws Exception {
        //MockitoAnnotations are used to innitialise the mocks
        MockitoAnnotations.initMocks(this);
        //create fields in track list
        track = new Track();
        track.setTrackId(23);
        track.setTrackName("John");
        track.setComments("THis is another comment");
        list = new ArrayList<>();
        list.add(track);
        track.setTrackId(25);
        track.setTrackName("Joe");
        track.setComments("Some Comment");
        list.add(track);


    }

    @After
    public void tearDown() throws Exception{
        list=null;
    }

//THIS test asserts the track, whether it is saved or not
    @Test
    public void givenATrackShouldReturnSaveTrack() throws TrackIdAlreadyExistsException {
            when(trackRepository.save((Track) any())).thenReturn(track);
            Track saveTrack = trackService.saveTrack(track);
            Assert.assertEquals(track, saveTrack);
            verify(trackRepository, times(1)).save(track);

    }

    //THIS test asserts the track and sends exception, if track already exists
@Test(expected =TrackIdAlreadyExistsException.class)
public void givenATrackShouldReturnFailedIfTrackNotSaved() throws TrackIdAlreadyExistsException
{
//    Track track1=new Track(23,"Joe","Some Comment");
    //Arrange
    when(trackRepository.existsById(track.getTrackId())).thenReturn(true);
    when(trackRepository.save((Track) any())).thenReturn(track);
    //Act
    Track savedTrack = trackService.saveTrack(track);
    System.out.println("savedTrack" + savedTrack);
    //Assert
    Assert.assertEquals(track,savedTrack);
    verify(trackRepository,times(1)).save(track);
}
    //THIS test should return track, if it exists by ID
@Test
    public void givenATrackShouldReturnTrackIfTrackIdIsFound() throws TrackNotFoundException{
        trackRepository.save(track);
        Track track4=new Track(25,"Joe","Some Comment");
        when(trackRepository.existsById(25)).thenReturn(true);
        when(trackRepository.findById(25)).thenReturn(Optional.of(track4));
        Assert.assertEquals(track4,track);
        verify(trackRepository,times(1)).save(track4);

}
//THIS test gives exception if track is not found by Id
//THe exception should be trackNotFound
    @Test(expected =TrackNotFoundException.class)
    public void givenATrackShouldReturnExceptionIfTrackIdNotFound() throws TrackNotFoundException{
        when(trackRepository.existsById(28)).thenReturn(false);
        when(trackRepository.findById(28)).thenReturn(Optional.ofNullable(track));
        Optional<Track> getBYId=trackService.getTrackById(28);
        Assert.assertEquals("Track Not Found Exception",getBYId);
        verify(trackRepository,times(1)).save(track);
    }

    //THIS test isused to check whether it gives us all tracks at once
    @Test
    public void givenATrackShouldReturnAllTracks() throws Exception {
        trackRepository.save(track);
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> music=trackService.getAllTracks();
        Assert.assertEquals(list,music);
        verify(trackRepository,times(1)).save(track);
    }
    //If no track is present, it must throw an exception
    @Test(expected = Exception.class)
    public void givenATrackShouldReturnExceptionIfNoTrackIsPresent() throws Exception{
        when(trackRepository.equals(null)).thenReturn(false);
        when(trackRepository.findAll()).thenReturn((List<Track>) track);
        List<Track> getAllTracks=trackService.getAllTracks();
        Assert.assertEquals(Exception.class,getAllTracks);
        verify(trackRepository,times(1)).save(track);
    }

    //This track tests for updation of fields by Id
    @Test
    public void givenATrackShouldReturnUpdatedTrack() throws TrackNotFoundException
    {

        trackRepository.save(track);
        Track track1 = new Track();
        track1.setTrackName("Better");
        track1.setComments("Best Track");
        when(trackRepository.findById(track.getTrackId())).thenReturn(Optional.of(track));
        Track updateTrack =  trackService.updateTrack(25,track1);
        when(trackRepository.save(updateTrack)).thenReturn(updateTrack);
        Assert.assertNotEquals(updateTrack,track);
        verify(trackRepository,times(1)).save(track);
    }

    //THIS test asserts the track, whether it is saved or not
    @Test
    public void givenATrackShouldDeleteTrackById() throws TrackNotFoundException
    {
        //save the track and find it by using existsById..
        trackRepository.save(track);
        when(trackRepository.existsById(track.getTrackId())).thenReturn(true);
        when(trackRepository.findById(track.getTrackId())).thenReturn(java.util.Optional.of(track));
        Optional<Track> track1 = trackService.trackDeleteById(track.getTrackId());
        Assert.assertEquals(true,trackRepository.existsById(track.getTrackId()));
        verify(trackRepository,times(1)).save(track);

    }

    //THIS test is performed to check whether we get track details by name or not
    @Test
    public void givenATrackNameShouldReturnTheTrackIfPresent() throws TrackNotFoundException, TrackIdAlreadyExistsException {
        trackService.saveTrack(track);
        when(trackRepository.findByTrackName("Joe")).thenReturn(Collections.singletonList(track));
        //retrieve a trackList which represents actual list by name
        List<Track> trackList= trackService.getTrackByName("Joe");
        //CReated an expected track list by adding details
        track=new Track();
        track.setTrackId(25);
        track.setTrackName("Joe");
        track.setComments("Some Comment");
        List<Track> expectedTrack=new ArrayList<Track>();
        expectedTrack.add(track);
        //Assert the expected with actual list
        Assert.assertEquals(expectedTrack,trackList);
        verify(trackRepository,times(3)).findByTrackName("Joe");

    }
}


