package com.fauzia.project.backend.converter;

import com.fauzia.project.backend.dto.TravelPackageDTO;
import com.fauzia.project.backend.dto.TravelPackageGalleryDTO;
import com.fauzia.project.backend.model.TravelPackageGalleryModel;
import com.fauzia.project.backend.model.TravelPackageModel;
import com.fauzia.project.backend.repository.TravelPackageGalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TravelPackageConverter {

    @Autowired
    private TravelPackageGalleryRepository travelPackageGalleryRepository;

    public TravelPackageDTO convertDTO(TravelPackageModel travelPackageModel){
        TravelPackageDTO packageDTO = new TravelPackageDTO();
        List<TravelPackageGalleryDTO> results = new ArrayList<>();
        packageDTO.setTravelId(travelPackageModel.getTravelId());
        packageDTO.setTravelTitle(travelPackageModel.getTravelTitle());
        packageDTO.setTravelLocation(travelPackageModel.getTravelLocation());

        Optional<TravelPackageGalleryModel> galleryOpt = travelPackageGalleryRepository.getImgLimit(travelPackageModel.getTravelId());
        TravelPackageGalleryDTO galleryDTO = new TravelPackageGalleryDTO();

        //validasi
        if (galleryOpt.isPresent()){
            galleryDTO.setGalleryId(galleryOpt.get().getGalleryId());
            galleryDTO.setGalleryImage(galleryOpt.get().getGalleryImage());
            packageDTO.setGallery(galleryDTO);
        }

        return packageDTO;

    }
}
