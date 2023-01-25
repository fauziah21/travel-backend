package com.fauzia.project.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {
    private int userId;
    private String userEmail;
    private String userName;
    private String status;
    private String userRole;
    private String jwtToken;
    private String jwtTokenRequest;
}
