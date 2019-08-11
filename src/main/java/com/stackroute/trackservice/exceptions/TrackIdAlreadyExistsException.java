package com.stackroute.trackservice.exceptions;
// Create an Exception for Track ID Already exists by extending root Exception
public class TrackIdAlreadyExistsException extends Exception{
    //Create a string message to send the output exception
    private String message;
    public TrackIdAlreadyExistsException() {
    }
    public TrackIdAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }


}


