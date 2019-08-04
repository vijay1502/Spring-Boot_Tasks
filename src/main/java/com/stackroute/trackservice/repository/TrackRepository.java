package com.stackroute.trackservice.repository;

import com.stackroute.trackservice.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {

//    @Query("select t from Track t where t.trackName = ?1")
//    List<Track> getTrackByName(String trackName);
    @Query(value = "select u from users u where u.trackName=?",nativeQuery = true)
    List<Track> getTrackByName(String trackName);
}
