package com.stackroute.trackservice.service;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImpl implements TrackService {
    private TrackRepository trackRepository;


    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository){
        this.trackRepository=trackRepository;
    }
    @Override
    public Track saveTrack(Track track){
        Track savedTrack=trackRepository.save(track);
        return savedTrack;
    }
    @Override
    public Track getTrackById(int trackId){
        Track userTrackId=trackRepository.findById(trackId).get();
        return userTrackId;
    }
 /*   @Override
    public List<Track> getAllTracks(int trackId) {
       List<Track> trackList=trackRepository.findAll();
       return trackList;
    }*/

@Override
    public List<Track> getAllTracks(){
    List<Track> trackList=trackRepository.findAll();
    return trackList;
}

@Override
public Optional<Track> trackDeleteById(int trackId) {
    Optional<Track> trackDelete = trackRepository.findById(trackId);
    trackRepository.deleteById(trackId);
    return trackDelete;
}
    @Override
    public Track updateTrack(int id, Track track) {
        Track update = trackRepository.findById(id).get();
        update.setTrackName(track.getTrackName());
        update.setComments(track.getComments());
        return trackRepository.save(track);
    }

@Override
    public Track getTrackByName(String trackName){
    Track trackByName=trackRepository.getTrackByName(trackName);
    return trackByName;
}
}
