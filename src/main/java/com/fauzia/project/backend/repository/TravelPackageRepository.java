package com.fauzia.project.backend.repository;

import com.fauzia.project.backend.model.TravelPackageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TravelPackageRepository extends JpaRepository<TravelPackageModel, Integer> {

    @Query(value = "select * from backend.travel_package tp \n" +
            "where tp.travel_location like %:travelLocation%", nativeQuery = true)
    List<TravelPackageModel> getByLocation(String travelLocation);
}
