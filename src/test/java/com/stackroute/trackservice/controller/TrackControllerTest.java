package com.stackroute.trackservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.GlobalExceptions;
import com.stackroute.trackservice.exceptions.TrackIdAlreadyExistsException;
import com.stackroute.trackservice.exceptions.TrackNotFoundException;
import com.stackroute.trackservice.service.TrackService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
//@RunWith annotaion is used to define on which class we are running the test(Spring)
@RunWith(SpringRunner.class)
//Tests annotated with @WebMvcTest will also auto-configure Spring Security
@WebMvcTest
//We create trackControllerTeat and auto wire them with mockMVC and Track and declare them private based on their accessibility
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private Track track;
    //@MockBean creates a mocking object and inject them into track Controller
    @MockBean
    private TrackService trackService;
    @InjectMocks
    private TrackController trackController;
    //We use track List and initialise to null
    List<Track> list=null;

    @Before
    public void setUp() throws Exception {
//MockitoAnnotations are used to innitialise the mocks
        MockitoAnnotations.initMocks(this);
//MockMvcBuilders allows full control over the instantiation and initialization of controllers and their dependencies
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).setControllerAdvice(new GlobalExceptions()).build();
        //Create an Array List and add elements to it
        list=new ArrayList<>();
        track=new Track(1,"Saahore","Baahubali2");
        list.add(track);
        Track track1=new Track(2,"Nainowale ne","Padmavat");
        list.add(track1);
        Track track2=new Track(3,"Tere Sang Yaraa","Rustom");
        list.add(track2);

            }
            //After annotation will teardown all the elements before the program terminates an set them to null
    @After
    public void tearDown() throws Exception{
        track=null;
        list=null;
    }

    @Test
//In the below given test, the posted track should be saved
    public void givenATrackShouldReturnSaveTrack() throws Exception {
        //If track is saved, then it should return the details of track
        when(trackService.saveTrack((Track) any())).thenReturn(track);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
        /*Verify in Mockito simply means that you want to check if a
        certain method of a mock object has been called by specific number of times
         */
        verify(trackService,times(2)).saveTrack(track);
    }
//The given test checks for failure condition if the track is not saved
    @Test
    public void givenATrackShouldReturnFailedIfTrackNotSaved() throws Exception {
        //We throw an exception-trackId already exists if we repeatedly save the same track
        when(trackService.saveTrack((Track)any())).thenThrow(TrackIdAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON).
                        content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).saveTrack(track);
    }
//IN this test,we check whether the method is working to retrieve all tracks at once
    @Test
    public void givenATrackShouldReturnAllTracks() throws Exception{
        //We return list by using getAllTracks method
        when(trackService.getAllTracks()).thenReturn(list);
       mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tracks/")
                .contentType(MediaType.APPLICATION_JSON).
                       content(asJsonString(track)))
               .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).getAllTracks();
    }
//THis method test will give exception if tracks are not available
    @Test
    public void givenATrackShouldReturnExceptionIfNoTrackIsPresent() throws Exception{
        when(trackService.getAllTracks()).thenThrow(Exception.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tracks/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isGone())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).getAllTracks();
    }

//While using track by ID,if the track is not fond, then it should throw an exception
    @Test
    public void givenATrackShouldReturnExceptionIfTrackIdNotFound() throws Exception{
        when(trackService.getTrackById(1)).thenThrow(TrackNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/track/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).getTrackById(1);
    }

//THis test method checks whether the track is been properly deleted by ID or not
    @Test
    public void givenATrackShouldDeleteTrackById() throws Exception
    {
        when(trackService.trackDeleteById(track.getTrackId())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tracks/1",track.getTrackId())
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(2)).trackDeleteById(1);
    }

//If the ID Is not available for deletion, then it should throw an exception
    @Test
    public void givenATrackShouldReturnExceptionIfTrackIdIsNotPresentToDelete() throws Exception{
        when(trackService.trackDeleteById(1)).thenThrow(TrackNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tracks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(1)).trackDeleteById(1);
    }
    //This method checks whether the track is getting updated by its ID or not
    @Test
    public void givenATrackShouldReturnUpdatedTrack() throws Exception
    {

        when(trackService.updateTrack(1,track)).thenReturn(track);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/tracker/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService,times(2)).updateTrack(1,track);
    }
//If Id Is not found, the updation test should throw an exception as not found
    @Test
    public void givenATrackShouldReturnExceptionIfUpdationCannotBeDone() throws Exception{
        when(trackService.updateTrack(1,track)).thenThrow(TrackNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/trackUp/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
         verify(trackService,times(0)).updateTrack(3,track);
    }

    private static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }


}
//This test method checks whether the particular track is found by Id or not
@Test
    public void givenAStringShouldReturnTrackById() throws Exception{
        when(trackService.getTrackById(1)).thenReturn(null);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/track/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    verify(trackService,times(2)).getTrackById(1);


}
}
