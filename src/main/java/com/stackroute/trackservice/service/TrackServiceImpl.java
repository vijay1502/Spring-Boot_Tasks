package com.stackroute.trackservice.service;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackIdAlreadyExistsException;
import com.stackroute.trackservice.exceptions.TrackNotFoundException;
import com.stackroute.trackservice.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//@Service annotation use to write service components which should implement trackService methods
@Service
public class TrackServiceImpl implements TrackService {
    //To be accessible only for classes in particular packages, we use private access modifier
    private TrackRepository trackRepository;

//If connections are present, Wiring between different classes have to be done using @AutoWired annotation 
    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }
   //Override the methods using @Override annotation
  //This method will save the given track and throws exception if Id Already exists
    @Override
    public Track saveTrack(Track track) throws TrackIdAlreadyExistsException {
        if (trackRepository.existsById(track.getTrackId())) {
            throw new TrackIdAlreadyExistsException("Track Id Already Exists, Try new One");
        }
        Track savedTrack = trackRepository.save(track);
        return savedTrack;
    }
    
    //This method gives track details, by Considering Id as INput
    //If Id is not found, it should throw an exception 
    @Override
    public Optional<Track> getTrackById(int trackId) throws TrackNotFoundException {


        if (!trackRepository.findById(trackId).isPresent()) {
            throw new TrackNotFoundException("Track Not Found Exception");
        }
        return trackRepository.findById(trackId);
    }
 /*   @Override
    public List<Track> getAllTracks(int trackId) {
       List<Track> trackList=trackRepository.findAll();
       return trackList;
    }*/
    
     //This method gives all tracks details at once without any input
    //If no tracks are available, it should throw an exception 

    @Override
    public List<Track> getAllTracks() throws Exception {
        List<Track> trackList = trackRepository.findAll();
        System.out.println("****" + trackList);
        if (trackList.isEmpty()) {
            throw new Exception("TrackS NOT fOUnd");
        }
        return trackList;
    }
    
     //This method should delete track By Id and give remaining tracks
    //If Id is not found, it should throw an exception

    @Override
    public Optional<Track> trackDeleteById(int trackId) throws TrackNotFoundException {
        Optional<Track> trackDelete = trackRepository.findById(trackId);
        if (!trackDelete.isPresent()) {
            throw new TrackNotFoundException("Track Not Found Exception");

        }
        else {
            trackRepository.deleteById(trackId);
        }
        return trackDelete;
    }
    
     //This method updates a given track, and show the output
    //If Id is not found, it should throw an exception 

    @Override
    public Track updateTrack(int trackId, Track track) throws TrackNotFoundException {

        if (!trackRepository.findById(trackId).isPresent()) {
            throw new TrackNotFoundException("Track Not Found Exception");
        }
        else {
        Track update = trackRepository.findById(trackId).get();
        update.setTrackName(track.getTrackName());
        update.setComments(track.getComments());
        return trackRepository.save(track);}
    }

    
     //using trackname,we get the track details
    //If Id is not found, it should throw an exception 
    @Override
    public List<Track> getTrackByName(String trackName) throws TrackNotFoundException {
        List<Track> trackByName = trackRepository.getTrackByName(trackName);
            if (!trackByName.contains(trackName)) {
            throw new TrackNotFoundException("Track Not Found Exception");
        }
        return trackByName;
    }
}

