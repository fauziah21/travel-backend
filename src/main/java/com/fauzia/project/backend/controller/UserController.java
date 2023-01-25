package com.fauzia.project.backend.controller;

import com.fauzia.project.backend.dto.HandlerAuthDTO;
import com.fauzia.project.backend.dto.LoginDTO;
import com.fauzia.project.backend.dto.RegisterDTO;
import com.fauzia.project.backend.dto.request.UserRequestDTO;
import com.fauzia.project.backend.response.DataResponse;
import com.fauzia.project.backend.response.HandlerResponse;
import com.fauzia.project.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @PostMapping("/register")
    public void registerUser(HttpServletRequest request, HttpServletResponse response,
                             @RequestBody UserRequestDTO userRequestDTO){
        HandlerAuthDTO user = userService.registerUser(userRequestDTO);
        switch (user.getErrorCode()){
            case "00":
                DataResponse<RegisterDTO> dataResponse = new DataResponse<>();
                dataResponse.setPayload(user.getUserDTO());
                HandlerResponse.responseSuccessWithData(response, dataResponse, "Success");
                break;
            case "01":
                HandlerResponse.responseBadRequest(response, "01", "Email already exists");
                break;
            case "02":
                HandlerResponse.responseBadRequest(response, "02", "Empty field");
                break;
            case "03":
                HandlerResponse.responseBadRequest(response, "03", "name must be at least 4 characters");
                break;
            case "04":
                HandlerResponse.responseBadRequest(response, "04", "password must be at least 6 characters");
                break;
            default:
                HandlerResponse.responseBadRequest(response, "05", "Error");
        }
    }

    @PostMapping("/login")
    public void successAuthenticationResponse(HttpServletResponse response,@RequestParam("userEmail")String userEmail,
                                              @RequestParam("userPassword")String userPassword){
        LoginDTO login = userService.login(userEmail, userPassword);
        if (login == null){
            HandlerResponse.responseBadRequest(response, "06", "Wrong Email or Password");
        }else{
            DataResponse<LoginDTO> dataResponse = new DataResponse<>();
            dataResponse.setPayload(login);
            HandlerResponse.responseSuccessWithData(response, dataResponse, "Success");
        }
    }
}
