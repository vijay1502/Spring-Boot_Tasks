package com.stackroute.trackservice.service;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.exceptions.TrackIdAlreadyExistsException;
import com.stackroute.trackservice.exceptions.TrackNotFoundException;

import java.util.List;
import java.util.Optional;
//Interface for Track Service which should be implemented by TrackService Implementation
public interface TrackService {
    //Abstract Methods with their Exceptions
    //Abstract Method Save Track is used to save a particular track after posting it
    //If track is already in existence, then it must throw an exception
    public Track saveTrack(Track track) throws TrackIdAlreadyExistsException;
    //GetTrackById should provide track details after posting the trackId
    //If the trackId is not found, then it should throw an exception
    public Optional<Track> getTrackById (int trackId)throws TrackNotFoundException;
    //Using trackId,(or Name) should get all the tracks
    //If the tracks are null or not there, then it should throw an exception
    public List<Track> getAllTracks() throws Exception;
    //Using TrackId, should delete and show rest of the tracks
    //If Id is not there, Track Not Found exception should come
    public Optional<Track> trackDeleteById(int trackId) throws TrackNotFoundException;
    //Using Track ID of particular track, we should update the track
    //If track is not found, it must throw an exception
    public Track updateTrack(int trackId,Track track) throws TrackNotFoundException;
    //Using track name, we must be able to find the track
    //Else should throw an error that the track is not found
    public List<Track> getTrackByName(String trackName) throws TrackNotFoundException;
}
