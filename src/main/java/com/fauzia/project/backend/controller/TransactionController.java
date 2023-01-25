package com.fauzia.project.backend.controller;

import com.fauzia.project.backend.dto.TransactionDTO;
import com.fauzia.project.backend.dto.request.TransactionRequestDTO;
import com.fauzia.project.backend.dto.request.TxnWithDetailsDTO;
import com.fauzia.project.backend.response.DataResponse;
import com.fauzia.project.backend.response.HandlerResponse;
import com.fauzia.project.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public void create(HttpServletRequest request, HttpServletResponse response,
                       @RequestBody TransactionRequestDTO transactionRequestDTO){
        boolean txn = transactionService.create(transactionRequestDTO);

        if (txn){
            HandlerResponse.responseSuccessOK(response, "Success");
        }else {
            HandlerResponse.responseBadRequest(response, "004", "Data Not Found");
        }
    }

    //change transaction status
    @PatchMapping("/update/status-txn")
    public void updateTxnStatus(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("transactionId")int transactionId,
                                @RequestParam("transactionStatus")String transactionStatus,
                                @RequestParam("createBy")int createBy){
        boolean txnStatus = transactionService.updateTxnStatus(transactionId, transactionStatus, createBy);

        if (txnStatus){
            HandlerResponse.responseSuccessOK(response, "Update Successful");
        }else{
            HandlerResponse.responseBadRequest(response, "004", "Data Not Found");
        }
    }

    //GET ALL TRANSACTION
    @GetMapping("")
    public void getAllTxna(HttpServletRequest request, HttpServletResponse response){
        List<TransactionDTO> transactions = transactionService.getAllTxn();
        DataResponse<List<TransactionDTO>> dataResponse = new DataResponse<>();
        dataResponse.setPayload(transactions);
        HandlerResponse.responseSuccessWithData(response, dataResponse, "Success");
    }

    //GET BY ID
    @GetMapping("/{transactionId}")
    public void getTxnDetail(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable("transactionId")int transactionId){
        TxnWithDetailsDTO txnDetail = transactionService.getTxnDetail(transactionId);

        if (txnDetail == null){
            HandlerResponse.responseBadRequest(response, "004", "Data Not Found");
        }else {
            DataResponse<TxnWithDetailsDTO> dataResponse = new DataResponse<>();
            dataResponse.setPayload(txnDetail);
            HandlerResponse.responseSuccessWithData(response, dataResponse, "Success");
        }
    }
}
