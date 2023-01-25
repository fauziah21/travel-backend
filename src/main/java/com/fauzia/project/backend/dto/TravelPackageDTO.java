package com.fauzia.project.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TravelPackageDTO {
    private int travelId;
    private String travelTitle;
    private String travelLocation;
    private TravelPackageGalleryDTO gallery;
}
