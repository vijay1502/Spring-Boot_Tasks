package com.stackroute.trackservice.service;

import com.stackroute.trackservice.domain.Track;

import java.util.List;
import java.util.Optional;

public interface TrackService {
    public Track saveTrack(Track track);
    public Track getTrackById(int trackId);
//    public List<Track> getAllTracks();
    public List<Track> getAllTracks();
    public Optional<Track> trackDeleteById(int trackId);
    public Track updateTrack(int trackId,Track track);
    public Track getTrackByName(String trackName);
}
