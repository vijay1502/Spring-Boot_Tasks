package com.stackroute.trackservice.controller;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackIdAlreadyExistsException;
import com.stackroute.trackservice.exceptions.TrackNotFoundException;
import com.stackroute.trackservice.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
//Use @RestController annotation is used to mark class as spring mvn controller
//@RestCocontroller is the combination of Controller and ResponseBody annotations
//RequestMapping annotation is used to map web requests onto specific handler classes and/or handler methods
@RestController
@RequestMapping(value = "api/v1/")
//@Request Mapping for providing the Request path to the Server

public class TrackController {
    
   ResponseEntity responseEntity;
    //Track Service is only accessible to this package, So we use it as private access modifier
    private TrackService trackService;
    public TrackController() {
    }
//The controller is to be auto wired with Track Service
    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }
    //The post Mapping annotation is used to post the track details
    @PostMapping("track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) {
    //Response Entity is used to receive response to the server
        
        try {
            //From track Service, using saveTrack method we save the posted track
            trackService.saveTrack(track);
            //If the track is posted, the http response is created successfully or using catch, we catch the conflict
            responseEntity=new ResponseEntity(trackService.saveTrack(track),HttpStatus.CREATED);
        }
        catch (TrackIdAlreadyExistsException trackIdExists){
            responseEntity=new ResponseEntity<String>(trackIdExists.getMessage(),HttpStatus.CONFLICT);
        }
        //Then return the response entity
        return responseEntity;
    }
    @GetMapping("track/{trackId}")
    public ResponseEntity<?> getTrack(@PathVariable int trackId){
        
        try {
            //Using TrackId we need to get the track details and http status are mentioned
            trackService.getTrackById(trackId);
            responseEntity=new ResponseEntity(trackService.getTrackById(trackId),HttpStatus.OK);
        }
        //If the Id is not flound, the exception should be caught
        catch (TrackNotFoundException trackNotFound){
            responseEntity=new ResponseEntity<String>(trackNotFound.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
   
  @GetMapping("tracks/")
    public ResponseEntity<?> getAllTracks(){
   //In this method, we need to to get details of all the tracks
        //For this,using Try clause, we provide the string as successfullt created 
      try{
      trackService.getAllTracks();
      responseEntity=new ResponseEntity("SuccessFully Retreived All THe DATA",HttpStatus.ACCEPTED);}
        //If no track is present, then exception must be caught
      catch (Exception ex){
          responseEntity=new ResponseEntity(ex.getMessage(),HttpStatus.GONE);
      }
      return responseEntity;
  }
//To delete the track details, this should be mapped using @DeleteMapping Annotation
  @DeleteMapping("tracks/{trackId}")
    public ResponseEntity<?> trackDelete(@PathVariable int trackId){
     //We delete a particular track by using its ID.
      try {
          //If deleted, then either pass the data or send the string as successfully deleted
          trackService.trackDeleteById(trackId);
          responseEntity=new ResponseEntity("Succesfull deletion of data",HttpStatus.CREATED);
      }
        //Else catch the exception as ID not found to delete
      catch (TrackNotFoundException trackNotFound){
          responseEntity=new ResponseEntity(trackNotFound.getMessage(),HttpStatus.METHOD_NOT_ALLOWED);
      }
     return responseEntity;
  }

//We use put,Patch mappings to update data using any of the variables in track
    @PutMapping("trackUp/{trackId}")
    public ResponseEntity<?> updateById(@PathVariable int trackId,@RequestBody Track track) {
      //If the track ID is present, the update the track using its ID and object
      try{
        trackService.updateTrack(trackId,track);
        responseEntity= new ResponseEntity("Successfully Updated", HttpStatus.OK);}
        //else send track not found message exception
      catch (TrackNotFoundException trackNotException){
          responseEntity=new ResponseEntity(trackNotException.getMessage(),HttpStatus.CONFLICT);
      }
      return responseEntity;
     }
    
    //The method below indicated track to be rendered using its name instead of ID
    @GetMapping("track/{trackName}")
    public ResponseEntity<?> trackByName(@PathVariable("trackName") String trackName){
      //If the name etered is present, then retrieve the message as successfully created 
      try{
      trackService.getTrackByName(trackName);
      responseEntity=new ResponseEntity("SuccessFull",HttpStatus.ACCEPTED);}
//else send an exception as track not found
      catch(TrackNotFoundException trackNotFound){
      responseEntity=new ResponseEntity(trackNotFound.getMessage(),HttpStatus.GONE);
    }
    return responseEntity;}

}


