package com.fauzia.project.backend.repository;

import com.fauzia.project.backend.model.TravelPackageGalleryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TravelPackageGalleryRepository extends JpaRepository<TravelPackageGalleryModel, Integer> {

    //get data berdasarkan packageId
    List<TravelPackageGalleryModel> findByTravelPackageId(int travelPackageId);

    @Query(value = "select * from backend.travel_package_gallery tpg \n" +
            "where tpg.travel_package_id = :travelPackageId limit 1", nativeQuery = true)
    Optional<TravelPackageGalleryModel> getImgLimit(@Param("travelPackageId")int travelPackageId);
}
