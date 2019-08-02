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

@RestController
@RequestMapping(value = "api/v1/")
//@Request Mapping for providing the Request path to the Server
public class TrackController {
    private TrackService trackService;
    public TrackController() {
    }

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }
    @PostMapping("track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) {
        ResponseEntity responseEntity;
        try {
            trackService.saveTrack(track);
            responseEntity=new ResponseEntity(trackService.saveTrack(track),HttpStatus.CREATED);
        }
        catch (TrackIdAlreadyExistsException trackIdExists){
            responseEntity=new ResponseEntity<String>(trackIdExists.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @GetMapping("track/{trackId}")
    public ResponseEntity<?> getTrack(@PathVariable int trackId){
        ResponseEntity responseEntity;
        try {
            trackService.getTrackById(trackId);
            responseEntity=new ResponseEntity(trackService.getTrackById(trackId),HttpStatus.CREATED);
        }
        catch (TrackNotFoundException trackNotFound){
            responseEntity=new ResponseEntity<String>(trackNotFound.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
   /* @GetMapping("tracks/")
    public ResponseEntity<?> getAllTracks(){
        Track getAllTracks=trackService.getAllTracks();
        return new ResponseEntity<>(getAllTracks,HttpStatus.CREATED);
    }*/
  @GetMapping("tracks/")
    public ResponseEntity<?> getAllTracks(){
      ResponseEntity responseEntity;
      try{
      trackService.getAllTracks();
      responseEntity=new ResponseEntity("SuccessFully Retreived All THe DATA",HttpStatus.CREATED);}
      catch (Exception ex){
          responseEntity=new ResponseEntity(ex.getMessage(),HttpStatus.CONFLICT);
      }
      return responseEntity;
  }

  @DeleteMapping("trackde/{trackId}")
    public ResponseEntity<?> trackDelete(@PathVariable int trackId){
      ResponseEntity responseEntity;
      try {
          trackService.trackDeleteById(trackId);
          responseEntity=new ResponseEntity("Succesfull deletion of data",HttpStatus.CREATED);
      }
      catch (TrackNotFoundException trackNotFound){
          responseEntity=new ResponseEntity(trackNotFound.getMessage(),HttpStatus.CONFLICT);
      }
     return responseEntity;
  }


    @PutMapping("tracker/{trackId}")
    public ResponseEntity<?> updateById(@PathVariable int trackId,@RequestBody Track track) {
      ResponseEntity responseEntity;
      try{
        trackService.updateTrack(trackId,track);
        responseEntity= new ResponseEntity("Successfully Updated", HttpStatus.OK);}
      catch (TrackNotFoundException trackNotException){
          responseEntity=new ResponseEntity(trackNotException.getMessage(),HttpStatus.CONFLICT);
      }
      return responseEntity;
    }
    @GetMapping("tracke/{trackName}")
    public ResponseEntity<?> trackByName(@PathVariable("trackName") String trackName){
      ResponseEntity responseEntity;
      try{
      trackService.getTrackByName(trackName);
      responseEntity=new ResponseEntity("SuccessFull",HttpStatus.CREATED);}

      catch(TrackNotFoundException trackNotFound){
      responseEntity=new ResponseEntity(trackNotFound.getMessage(),HttpStatus.CONFLICT);
    }
    return responseEntity;}

}




