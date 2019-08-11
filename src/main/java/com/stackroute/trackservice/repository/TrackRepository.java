package com.stackroute.trackservice.repository;

import com.stackroute.trackservice.domain.Track;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//@Repository annotation is used to provide the mechanism for storage and manipulation operation on objects

@Repository
//In this scenario we use MOngoDb Repository to store our data
public interface TrackRepository extends MongoRepository<Track, Integer> {
//Insert a query Annotation for using the database for tracklist and find by its name
    List<Track> findByTrackName(String trackName);
}
