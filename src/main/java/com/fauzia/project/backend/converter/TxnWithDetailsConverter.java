package com.fauzia.project.backend.converter;

import com.fauzia.project.backend.dto.TransactionDetailDTO;
import com.fauzia.project.backend.dto.request.TxnWithDetailsDTO;
import com.fauzia.project.backend.model.TransactionDetailModel;
import com.fauzia.project.backend.model.TransactionModel;
import com.fauzia.project.backend.model.TravelPackageModel;
import com.fauzia.project.backend.model.UserModel;
import com.fauzia.project.backend.repository.TransactionDetailRepository;
import com.fauzia.project.backend.repository.TravelPackageRepository;
import com.fauzia.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TxnWithDetailsConverter {
    @Autowired
    private TravelPackageRepository travelPackageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    public TxnWithDetailsDTO convertDTO(TransactionModel txnModel){
        //find travel package model
        Optional<TravelPackageModel> travelOpt = travelPackageRepository.findById(txnModel.getTravelPackageId());
        String title = travelOpt.get().getTravelTitle();
        LocalDate departureDate = travelOpt.get().getTravelDepartureDate();
        String duration = travelOpt.get().getTravelDuration();
        String type = travelOpt.get().getTravelType();

        //find user
        Optional<UserModel> userOpt = userRepository.findById(txnModel.getUserId());
        String user = userOpt.get().getUserName();

        TxnWithDetailsDTO txnWithDetailsDTO = new TxnWithDetailsDTO();
        txnWithDetailsDTO.setTransactionId(txnModel.getTransactionId());
        txnWithDetailsDTO.setTravelTitle(title);
        txnWithDetailsDTO.setUserName(user);
        txnWithDetailsDTO.setTransactionTotal(txnModel.getTransactionTotal());
        txnWithDetailsDTO.setTransactionStatus(txnModel.getTransactionStatus());
        txnWithDetailsDTO.setTravelDepartureDate(departureDate);
        txnWithDetailsDTO.setTravelDuration(duration);
        txnWithDetailsDTO.setTravelType(type);
        txnWithDetailsDTO.setCreationDate(txnModel.getCreationDate());

        List<TransactionDetailModel> transactionDetails = transactionDetailRepository.findByTransactionId(txnModel.getTransactionId());
        List<Integer> txnDetailsIds = transactionDetails.stream().map(TransactionDetailModel::getTransactionDetailsId).collect(Collectors.toList());
        List<TransactionDetailDTO> results = new ArrayList<>();

        txnDetailsIds.forEach(txnDetailsId ->{
            TransactionDetailDTO txnDetailDTO = new TransactionDetailDTO();
            txnDetailDTO.setUserName(transactionDetailRepository.findById(txnDetailsId).get().getUserName());
            txnDetailDTO.setNationality(transactionDetailRepository.findById(txnDetailsId).get().getNationality());
            results.add(txnDetailDTO);
        });

        txnWithDetailsDTO.setDetails(results);

        return txnWithDetailsDTO;

    }
}
