package com.fauzia.project.backend.dto.request;

import com.fauzia.project.backend.dto.TransactionDetailDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TransactionRequestDTO {
    private int travelPackageId;
    private int userId;
    private String transactionStatus;
    public List<TransactionDetailDTO> payloads;
}
