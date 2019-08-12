package com.stackroute.trackservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
//Instead of creating constructor methods inside a class, we provide them noArgs and AllArgs Constructor annotations
//@PropertySource defines the class path to use a particular resource
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PropertySource("application.properties")


public class Track {
    //@Column defines the column name in the query
    @Id
    @Column(name = "trackId")
    @Value("trackId")

    private int trackId;
    @Column(name = "trackName")
    @Value("${value.trackId}")
    private String trackName;
    @Column(name = "comments")
    @Value("${value.comments}")
    private String comments;
}
