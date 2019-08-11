package com.stackroute.trackservice.repository;

import com.stackroute.trackservice.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
//@Repository annotation is used to provide the mechanism for storage and manipulation operation on objects
@Repository
//In this scenario we use h2 JPA Repository to store our data

public interface TrackRepository extends JpaRepository<Track, Integer> {
//Insert a query Annotation for using the database
    @Query("select t from Track t where t.trackName = ?1")
    //Method to specify what type of OPeration we want to perform
    List<Track> getTrackByName(String trackName);

}
