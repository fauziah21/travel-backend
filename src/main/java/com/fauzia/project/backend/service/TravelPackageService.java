package com.fauzia.project.backend.service;

import com.fauzia.project.backend.converter.TravelPackageConverter;
import com.fauzia.project.backend.converter.TravelPackageDetailConverter;
import com.fauzia.project.backend.dto.TravelPackageDTO;
import com.fauzia.project.backend.dto.TravelPackageDetailDTO;
import com.fauzia.project.backend.dto.request.TravelPackageRequestDTO;
import com.fauzia.project.backend.model.TravelPackageModel;
import com.fauzia.project.backend.repository.TravelPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TravelPackageService {
    @Autowired
    private TravelPackageRepository travelPackageRepository;
    @Autowired
    private TravelPackageDetailConverter travelPackageDetailConverter;
    @Autowired
    private TravelPackageConverter travelPackageConverter;

    //CREATE
    public TravelPackageDetailDTO create(TravelPackageRequestDTO travelPackageRequestDTO){
        TravelPackageModel travelPackageModel = new TravelPackageModel();
        travelPackageModel.setTravelTitle(travelPackageRequestDTO.getTravelTitle());
        travelPackageModel.setTravelLocation(travelPackageRequestDTO.getTravelLocation());
        travelPackageModel.setTravelAbout(travelPackageRequestDTO.getTravelAbout());
        travelPackageModel.setTravelFeaturedEvent(travelPackageRequestDTO.getTravelFeaturedEvent());
        travelPackageModel.setTravelDepartureDate(travelPackageRequestDTO.getTravelDepartureDate());
        travelPackageModel.setTravelDuration(travelPackageRequestDTO.getTravelDuration());
        travelPackageModel.setTravelType(travelPackageRequestDTO.getTravelType());
        travelPackageModel.setTravelPrice(travelPackageRequestDTO.getTravelPrice());
        travelPackageModel.setCreationDate(LocalDateTime.now());
        travelPackageModel.setLastUpdate(LocalDateTime.now());
        travelPackageModel.setCreateBy(travelPackageRequestDTO.getCreateBy());
        travelPackageModel.setUpdateBy(travelPackageRequestDTO.getUpdateBy());
        travelPackageModel.setStatus("active");

        return travelPackageDetailConverter.convertDTO(travelPackageRepository.save(travelPackageModel));

    }

    //UPDATE
    public boolean update(int travelId, TravelPackageRequestDTO travelPackageRequestDTO){
        Optional<TravelPackageModel> travelOpt = travelPackageRepository.findById(travelId);

        if (travelOpt.isEmpty()){
            return false;
        }else{
            TravelPackageModel travelModel = travelOpt.get();
            travelModel.setTravelTitle(travelPackageRequestDTO.getTravelTitle());
            travelModel.setTravelLocation(travelPackageRequestDTO.getTravelLocation());
            travelModel.setTravelAbout(travelPackageRequestDTO.getTravelAbout());
            travelModel.setTravelFeaturedEvent(travelPackageRequestDTO.getTravelFeaturedEvent());
            travelModel.setTravelDepartureDate(travelPackageRequestDTO.getTravelDepartureDate());
            travelModel.setTravelDuration(travelPackageRequestDTO.getTravelDuration());
            travelModel.setTravelType(travelPackageRequestDTO.getTravelType());
            travelModel.setTravelPrice(travelPackageRequestDTO.getTravelPrice());
            travelModel.setCreationDate(travelModel.getCreationDate());
            travelModel.setLastUpdate(LocalDateTime.now());
            travelModel.setCreateBy(travelModel.getCreateBy());
            travelModel.setUpdateBy(travelPackageRequestDTO.getUpdateBy());

            travelPackageRepository.save(travelModel);
            return true;
        }
    }

    //CHANGE STATUS
    public boolean updateStatus(int travelId, String status, int userId){
        Optional<TravelPackageModel> travelOpt = travelPackageRepository.findById(travelId);
        if (travelOpt.isEmpty()){
            return false;
        }else {
            TravelPackageModel travelModel = travelOpt.get();
            travelModel.setStatus(status);
            travelModel.setLastUpdate(LocalDateTime.now());
            travelModel.setUpdateBy(userId);

            travelPackageRepository.save(travelModel);
            return true;
        }
    }

    //get by id
    public TravelPackageDetailDTO getById(int travelId){
        Optional<TravelPackageModel> travelPaxOpt = travelPackageRepository.findById(travelId);

        if (travelPaxOpt.isEmpty()){
            return null;
        }else {
            return travelPackageDetailConverter.convertDTO(travelPaxOpt.get());
        }
    }

    //get by travelLocation
    public List<TravelPackageDTO> getByLocation(String travelLocation){
        List<TravelPackageModel> travels = travelPackageRepository.getByLocation(travelLocation);

        return travels.stream().map(travel -> travelPackageConverter.convertDTO(travel)).collect(Collectors.toList());

    }

    //get all
    public List<TravelPackageDTO> getAll(){
        List<TravelPackageModel> travels = travelPackageRepository.findAll();
        return travels.stream().map(travel -> travelPackageConverter.convertDTO(travel)).collect(Collectors.toList());
    }


}
