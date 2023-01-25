package com.fauzia.project.backend.converter;

import com.fauzia.project.backend.dto.LoginDTO;
import com.fauzia.project.backend.model.UserModel;
import com.fauzia.project.backend.utils.TokenUtils;
import org.springframework.stereotype.Component;

@Component
public class LoginConverter {
    public LoginDTO convertLoginDTO(UserModel userModel){
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId(userModel.getUserId());
        loginDTO.setUserEmail(userModel.getUserEmail());
        loginDTO.setUserName(userModel.getUserName());
        loginDTO.setStatus(userModel.getStatus());
        loginDTO.setUserRole(userModel.getUserRole());

        String token = TokenUtils.generateToken(userModel.getUserEmail());
        String refreshToken = TokenUtils.generateRefreshToken(userModel.getUserEmail());

        loginDTO.setJwtToken(token);
        loginDTO.setJwtTokenRequest(refreshToken);

        return loginDTO;
    }
}
