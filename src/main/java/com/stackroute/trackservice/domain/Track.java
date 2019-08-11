package com.stackroute.trackservice.domain;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.Entity;
import javax.persistence.Id;
//An entity is a plain old Java object (POJO) class that is mapped to the database

@Entity

//Create a class for track in domain package
public class Track {
    
    @Id
    //Create the fields in the track and make a contructor with args and without args
    private int trackId;
    private String trackName;
    private String comments;

    public Track(int trackId, String trackName, String comments) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.comments = comments;
    }
    //Call the methods for given variables using getters and setters
    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Track() {
    }
    //Over ride the given parameters to represent them in a string format
    @Override
    public String toString() {
        return "Track{" +
                "trackId=" + trackId +
                ", trackName='" + trackName + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
