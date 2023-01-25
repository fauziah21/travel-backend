package com.fauzia.project.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fauzia.project.backend.converter.LoginConverter;
import com.fauzia.project.backend.converter.RegisterConverter;
import com.fauzia.project.backend.dto.HandlerAuthDTO;
import com.fauzia.project.backend.dto.LoginDTO;
import com.fauzia.project.backend.dto.RegisterDTO;
import com.fauzia.project.backend.dto.request.UserRequestDTO;
import com.fauzia.project.backend.model.UserModel;
import com.fauzia.project.backend.repository.UserRepository;
import com.fauzia.project.backend.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegisterConverter registerConverter;
    @Autowired
    private LoginConverter loginConverter;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //REGISTER USER
    public HandlerAuthDTO registerUser(UserRequestDTO userRequestDTO){

        Optional<UserModel> userOpt = userRepository.findByUserEmail(userRequestDTO.getUserEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserModel userModel = new UserModel();
        String passwordHash = "";
        if (userOpt.isPresent()){
            //data already exist
            return new HandlerAuthDTO("01", new RegisterDTO());
        }else{
            if (userRequestDTO.getUserName().isEmpty() || userRequestDTO.getUserEmail().isEmpty() || userRequestDTO.getUserPassword().isEmpty()){
                //please fill the field
                return new HandlerAuthDTO("02", new RegisterDTO());
            }else{
                if (userRequestDTO.getUserName().length() < 4){
                    //name must be at least 4 characters
                    return new HandlerAuthDTO("03", new RegisterDTO());
                }else if (userRequestDTO.getUserPassword().length() < 6){
                    //password must be at least 6 characters
                    return new HandlerAuthDTO("04", new RegisterDTO());
                }else{
//                    String passwordEncoder = bCryptPasswordEncoder.encode(userRequestDTO.getUserPassword());
                    try {
                        passwordHash = passwordEncoder.encode(userRequestDTO.getUserPassword());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    userModel.setUserEmail(userRequestDTO.getUserEmail());
                    userModel.setUserName(userRequestDTO.getUserName());
                    userModel.setUserPassword(passwordHash);
                    userModel.setStatus("active");
                    userModel.setCreationDate(LocalDateTime.now());
                    userModel.setLastUpdate(LocalDateTime.now());
                    userModel.setUserRole(userRequestDTO.getUserRole());


                    RegisterDTO userDTO = registerConverter.convertDTO(userRepository.save(userModel));

                    return new HandlerAuthDTO("00", userDTO);
                }
            }

        }

    }


    //LOGIN
    public LoginDTO login(String userEmail, String userPassword){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Optional<UserModel> userOpt = userRepository.findByUserEmail(userEmail);
        if (userOpt.isPresent()){
            boolean result = bCryptPasswordEncoder.matches(userPassword, userOpt.get().getUserPassword());

            if (result) {
                return loginConverter.convertLoginDTO(userOpt.get());
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    //REFRESH TOKEN
    public boolean refreshToken(String token){
        try{
            Algorithm algorithm =Algorithm.HMAC512(SecurityUtils.REFRESH_SECRET.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt =verifier.verify(token);
            int userId = jwt.getClaim("userId").asInt();
            Optional<UserModel> userOpt = userRepository.findById(userId);

            if (userOpt.isPresent()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
