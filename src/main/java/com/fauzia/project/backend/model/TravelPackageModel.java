package com.fauzia.project.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel_package")
@Setter
@Getter
public class TravelPackageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private int travelId;

    @Column(name= "travel_title")
    private String travelTitle;

    @Column(name = "travel_location")
    private String travelLocation;

    @Column(name = "travel_about")
    @Type(type = "text")
    private String travelAbout;

    @Column(name = "travel_featured_event")
    private String travelFeaturedEvent;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    @Column(name = "travel_departure_date")
    private LocalDate travelDepartureDate;

    @Column(name = "travel_duration")
    private String travelDuration;

    @Column(name = "travel_type")
    private String travelType;

    @Column(name = "travel_price")
    private int travelPrice;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "create_by")
    private int createBy;

    @Column(name = "update_by")
    private int updateBy;

    @Column(name = "status", columnDefinition = "enum('active','nonactive') default 'active' ")
    private String status;
}
