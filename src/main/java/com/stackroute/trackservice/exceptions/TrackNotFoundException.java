package com.stackroute.trackservice.exceptions;
//If the track is not found, either by Name or ID, then return this Exception
public class TrackNotFoundException extends Exception{
    private String message;
    public TrackNotFoundException() {
    }
    public TrackNotFoundException(String message) {
        super(message);
        this.message = message;
    }


}