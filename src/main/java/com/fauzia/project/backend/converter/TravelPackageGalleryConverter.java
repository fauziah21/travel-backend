package com.fauzia.project.backend.converter;

import com.fauzia.project.backend.dto.TravelPackageGalleryDTO;
import com.fauzia.project.backend.model.TravelPackageGalleryModel;
import org.springframework.stereotype.Component;

@Component
public class TravelPackageGalleryConverter {

    public TravelPackageGalleryDTO convertDTO(TravelPackageGalleryModel galleryModel){
        TravelPackageGalleryDTO galleryDTO = new TravelPackageGalleryDTO();
        galleryDTO.setGalleryId(galleryModel.getGalleryId());
        galleryDTO.setGalleryImage(galleryModel.getGalleryImage());

        return galleryDTO;
    }
}
