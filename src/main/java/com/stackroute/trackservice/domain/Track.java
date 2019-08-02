package com.stackroute.trackservice.domain;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PropertySource("application.properties")
public class Track {
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
