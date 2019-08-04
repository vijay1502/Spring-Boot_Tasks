package com.stackroute.trackservice.controller;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackIdAlreadyExistsException;
import com.stackroute.trackservice.exceptions.TrackNotFoundException;
import com.stackroute.trackservice.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
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
//   @Autowired
//   public void trackQualifier(@Qualifier("dummy") TrackService trackService){
//        this.trackService=trackService;
//   }

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }
    @PostMapping("track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) throws TrackIdAlreadyExistsException {
        ResponseEntity responseEntity;

            trackService.saveTrack(track);
            responseEntity=new ResponseEntity(trackService.saveTrack(track),HttpStatus.CREATED);


        return responseEntity;
    }
    @GetMapping("track/{trackId}")
    public ResponseEntity<?> getTrack(@PathVariable int trackId) throws TrackNotFoundException {
        ResponseEntity responseEntity;

            trackService.getTrackById(trackId);
            responseEntity=new ResponseEntity(trackService.getTrackById(trackId),HttpStatus.CREATED);

                return responseEntity;
    }
   /* @GetMapping("tracks/")
    public ResponseEntity<?> getAllTracks(){
        Track getAllTracks=trackService.getAllTracks();
        return new ResponseEntity<>(getAllTracks,HttpStatus.CREATED);
    }*/
  @GetMapping("tracks/")
    public ResponseEntity<?> getAllTracks() throws Exception {
      ResponseEntity responseEntity;

      trackService.getAllTracks();
      responseEntity=new ResponseEntity(trackService.getAllTracks(),HttpStatus.CREATED);

      return responseEntity;
  }

  @DeleteMapping("trackde/{trackId}")
    public ResponseEntity<?> trackDelete(@PathVariable int trackId) throws TrackNotFoundException {
      ResponseEntity responseEntity;

          trackService.trackDeleteById(trackId);
          responseEntity=new ResponseEntity(trackService.trackDeleteById(trackId),HttpStatus.CREATED);


     return responseEntity;
  }


    @PutMapping("tracker/{trackId}")
    public ResponseEntity<?> updateById(@PathVariable int trackId,@RequestBody Track track) throws TrackNotFoundException {
      ResponseEntity responseEntity;

        trackService.updateTrack(trackId,track);
        responseEntity= new ResponseEntity(trackService.updateTrack(trackId,track), HttpStatus.OK);

      return responseEntity;
    }
    @GetMapping("tracke/{trackName}")
    public ResponseEntity<?> trackByName(@PathVariable("trackName") String trackName) throws TrackNotFoundException {
      ResponseEntity responseEntity;

      trackService.getTrackByName(trackName);
      responseEntity=new ResponseEntity(trackService.getTrackByName(trackName),HttpStatus.CREATED);


    return responseEntity;}

}




