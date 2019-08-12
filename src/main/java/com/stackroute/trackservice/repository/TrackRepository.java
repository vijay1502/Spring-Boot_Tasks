package com.stackroute.trackservice.repository;

import com.stackroute.trackservice.domain.Track;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
//Create a Mongo Repository for storing Data of Track
@Repository
public interface TrackRepository extends MongoRepository<Track, Integer> {


    List<Track> findByTrackName(String trackName);
}
