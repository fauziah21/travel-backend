package com.fauzia.project.backend.converter;

import com.fauzia.project.backend.dto.TransactionDTO;
import com.fauzia.project.backend.model.TransactionModel;
import com.fauzia.project.backend.model.TravelPackageModel;
import com.fauzia.project.backend.model.UserModel;
import com.fauzia.project.backend.repository.TravelPackageRepository;
import com.fauzia.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionConverter {
    @Autowired
    private TravelPackageRepository travelPackageRepository;
    @Autowired
    private UserRepository userRepository;

    public TransactionDTO convertDTO(TransactionModel txnModel){

        //find travel package model
        Optional<TravelPackageModel> travelOpt = travelPackageRepository.findById(txnModel.getTravelPackageId());
        String title = travelOpt.get().getTravelTitle();

        //find user
        Optional<UserModel> userOpt = userRepository.findById(txnModel.getUserId());
        String user = userOpt.get().getUserName();

        TransactionDTO txnDTO = new TransactionDTO();
        txnDTO.setTransactionId(txnModel.getTransactionId());
        txnDTO.setTravelTitle(title);
        txnDTO.setUserName(user);
        txnDTO.setTransactionTotal(txnModel.getTransactionTotal());
        txnDTO.setTransactionStatus(txnModel.getTransactionStatus());
        txnDTO.setCreationDate(txnModel.getCreationDate());
        txnDTO.setLastUpdate(txnModel.getLastUpdate());
        txnDTO.setCreateBy(txnModel.getCreateBy());
        txnDTO.setUpdateBy(txnModel.getUpdateBy());

        return txnDTO;
    }
}
