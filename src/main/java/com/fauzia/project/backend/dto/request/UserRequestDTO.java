package com.fauzia.project.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {
    private String userEmail;
    private String userName;
    private String userPassword;
    private String userRole;
}
