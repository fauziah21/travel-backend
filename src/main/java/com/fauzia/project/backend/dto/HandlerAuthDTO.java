package com.fauzia.project.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HandlerAuthDTO {
    private String errorCode;
    private RegisterDTO userDTO;

    public HandlerAuthDTO(String errorCode, RegisterDTO userDTO) {
        this.errorCode = errorCode;
        this.userDTO = userDTO;
    }
}
