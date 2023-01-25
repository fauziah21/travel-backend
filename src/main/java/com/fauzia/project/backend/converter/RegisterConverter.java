package com.fauzia.project.backend.converter;

import com.fauzia.project.backend.dto.RegisterDTO;
import com.fauzia.project.backend.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class RegisterConverter {
    public RegisterDTO convertDTO(UserModel userModel){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUserId(userModel.getUserId());
        registerDTO.setUserEmail(userModel.getUserEmail());
        registerDTO.setUserName(userModel.getUserName());
        registerDTO.setStatus(userModel.getStatus());
        registerDTO.setUserRole(userModel.getUserRole());

        return registerDTO;
    }
}
