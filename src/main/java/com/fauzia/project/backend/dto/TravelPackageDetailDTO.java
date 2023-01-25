package com.fauzia.project.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class TravelPackageDetailDTO {
    private int travelId;
    private String travelTitle;
    private String travelLocation;
    private String travelAbout;
    private String travelFeaturedEvent;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate travelDepartureDate;
    private String travelDuration;
    private String travelType;
    private int travelPrice;
    private String status;
    private List<TravelPackageGalleryDTO> galleries;
}
