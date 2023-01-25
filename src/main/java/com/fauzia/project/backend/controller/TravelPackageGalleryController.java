package com.fauzia.project.backend.controller;

import com.fauzia.project.backend.dto.TravelPackageGalleryDTO;
import com.fauzia.project.backend.response.DataResponse;
import com.fauzia.project.backend.response.HandlerResponse;
import com.fauzia.project.backend.service.TravelPackageGalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Handler;

@RestController
@RequestMapping("/api/v1/gallery")
public class TravelPackageGalleryController {

    @Autowired
    private TravelPackageGalleryService travelPackageGalleryService;

    @PostMapping("/create")
    public void create(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam("travelId")int travelId, @RequestParam("image") MultipartFile image,
                       @RequestParam("userId")int userId){
        boolean gallery = travelPackageGalleryService.create(travelId, image, userId);

        if (gallery){
            HandlerResponse.responseSuccessOK(response, "Success");
        }else {
            HandlerResponse.responseBadRequest(response, "05", "Error");
        }
    }

    //get all
    @GetMapping("/all")
    public void getAll(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam("travelPackageId")int travelPackageId){
        List<TravelPackageGalleryDTO> galleries = travelPackageGalleryService.getAll(travelPackageId);

        DataResponse<List<TravelPackageGalleryDTO>> dataResponse = new DataResponse<>();
        dataResponse.setPayload(galleries);
        HandlerResponse.responseSuccessWithData(response, dataResponse, "Success");
    }

    //HARD DELETE
    @DeleteMapping("/delete/{galleryId}")
    public void delete(HttpServletRequest request, HttpServletResponse response, @PathVariable("galleryId")int galleryId){
        boolean gallery = travelPackageGalleryService.delete(galleryId);

        if (gallery){
            HandlerResponse.responseSuccessOK(response, "Data Deleted");
        }else{
            HandlerResponse.responseBadRequest(response, "004", "Data Not Found");
        }
    }
}
