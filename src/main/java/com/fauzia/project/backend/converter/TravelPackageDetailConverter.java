package com.fauzia.project.backend.converter;

import com.fauzia.project.backend.dto.TravelPackageDetailDTO;
import com.fauzia.project.backend.dto.TravelPackageGalleryDTO;
import com.fauzia.project.backend.model.TravelPackageGalleryModel;
import com.fauzia.project.backend.model.TravelPackageModel;
import com.fauzia.project.backend.repository.TravelPackageGalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TravelPackageDetailConverter {
    @Autowired
    private TravelPackageGalleryRepository travelPackageGalleryRepository;

    public TravelPackageDetailDTO convertDTO(TravelPackageModel travelPackageModel){
        TravelPackageDetailDTO travelDTO = new TravelPackageDetailDTO();
        List<TravelPackageGalleryDTO> results = new ArrayList<>();

        travelDTO.setTravelId(travelPackageModel.getTravelId());
        travelDTO.setTravelTitle(travelPackageModel.getTravelTitle());
        travelDTO.setTravelLocation(travelPackageModel.getTravelLocation());
        travelDTO.setTravelAbout(travelPackageModel.getTravelAbout());
        travelDTO.setTravelFeaturedEvent(travelPackageModel.getTravelFeaturedEvent());
        travelDTO.setTravelDepartureDate(travelPackageModel.getTravelDepartureDate());
        travelDTO.setTravelDuration(travelPackageModel.getTravelDuration());
        travelDTO.setTravelType(travelPackageModel.getTravelType());
        travelDTO.setTravelPrice(travelPackageModel.getTravelPrice());
        travelDTO.setStatus(travelPackageModel.getStatus());

        List<TravelPackageGalleryModel> galleryModels = travelPackageGalleryRepository.findByTravelPackageId(travelPackageModel.getTravelId());
        List<Integer> galleries = galleryModels.stream().map(TravelPackageGalleryModel::getGalleryId).collect(Collectors.toList());

        galleries.forEach(gallery ->{
            TravelPackageGalleryDTO galleryDTO = new TravelPackageGalleryDTO();
            galleryDTO.setGalleryId(travelPackageGalleryRepository.findById(gallery).get().getGalleryId());
            galleryDTO.setGalleryImage(travelPackageGalleryRepository.findById(gallery).get().getGalleryImage());
            results.add(galleryDTO);
        });

        travelDTO.setGalleries(results);

        return travelDTO;
    }
}
