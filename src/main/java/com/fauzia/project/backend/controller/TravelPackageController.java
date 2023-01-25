package com.fauzia.project.backend.controller;

import com.fauzia.project.backend.dto.TravelPackageDTO;
import com.fauzia.project.backend.dto.TravelPackageDetailDTO;
import com.fauzia.project.backend.dto.request.TravelPackageRequestDTO;
import com.fauzia.project.backend.response.DataResponse;
import com.fauzia.project.backend.response.HandlerResponse;
import com.fauzia.project.backend.service.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/travel-package")
public class TravelPackageController {

    @Autowired
    private TravelPackageService travelPackageService;

    @PostMapping("/create")
    public void create(HttpServletRequest request, HttpServletResponse response,
                       @RequestBody TravelPackageRequestDTO travelPackageRequestDTO){
        TravelPackageDetailDTO travel = travelPackageService.create(travelPackageRequestDTO);

        DataResponse<TravelPackageDetailDTO> dataResponse = new DataResponse<>();
        dataResponse.setPayload(travel);
        HandlerResponse.responseSuccessWithData(response, dataResponse, "Success");

    }

    @PutMapping("/update/{travelId}")
    public void update(HttpServletRequest request, HttpServletResponse response, @PathVariable("travelId")int travelId,
                       @RequestBody TravelPackageRequestDTO travelPackageRequestDTO){
        boolean update = travelPackageService.update(travelId,travelPackageRequestDTO);
        if (update){
            HandlerResponse.responseSuccessOK(response, "Success");
        }else {
            HandlerResponse.responseBadRequest(response, "004", "Data Not Found");
        }
    }

    //CHANGE STATUS
    @PatchMapping("/update/status/{travelId}")
    public void updateStatus(HttpServletRequest request, HttpServletResponse response, @PathVariable("travelId")int travelId,
                             @RequestParam("status")String status, @RequestParam("userId")int userId){
        boolean result = travelPackageService.updateStatus(travelId, status, userId);

        if (result){
            HandlerResponse.responseSuccessOK(response, "Success");
        }else {
            HandlerResponse.responseBadRequest(response, "004", "Data Not Found");
        }
    }

    //GET BY ID
    @GetMapping("/{travelId}")
    public void getById(HttpServletRequest request, HttpServletResponse response,
                        @PathVariable("travelId")int travelId){
        TravelPackageDetailDTO travelPax = travelPackageService.getById(travelId);

        if (travelPax == null){
            HandlerResponse.responseBadRequest(response, "004", "Data Not Found");
        }else {
            DataResponse<TravelPackageDetailDTO> dataResponse = new DataResponse<>();
            dataResponse.setPayload(travelPax);
            HandlerResponse.responseSuccessWithData(response, dataResponse, "Success");
        }
    }

    //get by location
    @GetMapping("/location/{travelLocation}")
    public void getByLocation(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable("travelLocation") String travelLocation){
        List<TravelPackageDTO> travels = travelPackageService.getByLocation(travelLocation);

        DataResponse<List<TravelPackageDTO>> dataResponse = new DataResponse<>();
        dataResponse.setPayload(travels);
        HandlerResponse.responseSuccessWithData(response, dataResponse, "Success");
    }

    //get all
    @GetMapping("/all")
    public void getAll(HttpServletRequest request, HttpServletResponse response){
        List<TravelPackageDTO> travels = travelPackageService.getAll();

        DataResponse<List<TravelPackageDTO>> dataResponse = new DataResponse<>();
        dataResponse.setPayload(travels);
        HandlerResponse.responseSuccessWithData(response, dataResponse, "Success");
    }
}
