package com.stackroute.trackservice.seedData;

import com.stackroute.trackservice.domain.Track;
import com.stackroute.trackservice.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
//ApplicationListenerImpl should implement application listener to use seed data
public class ApplicationListenerImpl implements ApplicationListener {
    //Use Track Repository to send the data to its related database
    private TrackRepository trackRepository;
    //Autowired with track repository
    @Autowired
        public ApplicationListenerImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }
    //Use on application event and create the hard coded data
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        Track track5=new Track(1,"krishna","1st one");
        trackRepository.save(track5);
        Track track6=new Track(2,"Kashyap","2nd one");
        trackRepository.save(track6);
        Track track7=new Track(3,"vijay","3rd one");
        trackRepository.save(track7);
    }
}
