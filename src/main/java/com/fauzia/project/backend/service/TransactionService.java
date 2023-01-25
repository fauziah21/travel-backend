package com.fauzia.project.backend.service;

import com.fauzia.project.backend.converter.TransactionConverter;
import com.fauzia.project.backend.converter.TxnWithDetailsConverter;
import com.fauzia.project.backend.dto.TransactionDTO;
import com.fauzia.project.backend.dto.request.TransactionRequestDTO;
import com.fauzia.project.backend.dto.request.TxnWithDetailsDTO;
import com.fauzia.project.backend.model.TransactionDetailModel;
import com.fauzia.project.backend.model.TransactionModel;
import com.fauzia.project.backend.model.TravelPackageModel;
import com.fauzia.project.backend.repository.TransactionDetailRepository;
import com.fauzia.project.backend.repository.TransactionRepository;
import com.fauzia.project.backend.repository.TravelPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TravelPackageRepository travelPackageRepository;
    @Autowired
    private TransactionDetailRepository transactionDetailRepository;
    @Autowired
    private TransactionConverter transactionConverter;
    @Autowired
    private TxnWithDetailsConverter txnWithDetailsConverter;

    @Transactional
    public boolean create(TransactionRequestDTO transactionRequestDTO){
        /*
        * 1. store data ke tabel transaction
        * 2. store data ke tabel transaction detail dengan cara loop sesuai length*/

        //find the travel package
        Optional<TravelPackageModel> travelPackageOpt = travelPackageRepository.findById(transactionRequestDTO.getTravelPackageId());
        if (travelPackageOpt.isEmpty()){
            return false;
        }
        int price = travelPackageOpt.get().getTravelPrice();
        int totalPrice = transactionRequestDTO.getPayloads().size() * price;
        //store data ke tabel transaction
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setTravelPackageId(transactionRequestDTO.getTravelPackageId());
        transactionModel.setUserId(transactionRequestDTO.getUserId());
        transactionModel.setTransactionTotal(totalPrice);
        transactionModel.setTransactionStatus("onProcess");
        transactionModel.setCreationDate(LocalDateTime.now());
        transactionModel.setLastUpdate(LocalDateTime.now());
        transactionModel.setCreateBy(transactionRequestDTO.getUserId());
        transactionModel.setUpdateBy(transactionRequestDTO.getUserId());

        transactionRepository.save(transactionModel);

        //store data ke tabel transaction_details
//        List<TransactionDetailModel> data = new ArrayList<>();
        for (int i = 0; i < transactionRequestDTO.getPayloads().size(); i++){
            TransactionDetailModel txnDetailModel = new TransactionDetailModel();
            txnDetailModel.setTransactionId(transactionModel.getTransactionId());
            txnDetailModel.setUserName(transactionRequestDTO.getPayloads().get(i).getUserName());
            txnDetailModel.setNationality(transactionRequestDTO.getPayloads().get(i).getNationality());
            txnDetailModel.setCreationDate(LocalDateTime.now());
            txnDetailModel.setLastUpdate(LocalDateTime.now());
            txnDetailModel.setCreateBy(transactionRequestDTO.getUserId());
            txnDetailModel.setUpdateBy(transactionRequestDTO.getUserId());

//            data.add(txnDetailModel);
            transactionDetailRepository.save(txnDetailModel);
        }

        return true;
    }

    //change transactions status
    public boolean updateTxnStatus(int transactionId, String transactionStatus, int createBy){
        Optional<TransactionModel> transactionOpt = transactionRepository.findById(transactionId);
        if (transactionOpt.isEmpty()){
            return false;
        }else{
            TransactionModel txnModel = transactionOpt.get();
            txnModel.setTransactionStatus(transactionStatus);
            txnModel.setUpdateBy(createBy);
            txnModel.setLastUpdate(LocalDateTime.now());

            transactionRepository.save(txnModel);
            return true;
        }
    }

    //GET ALL TRANSACTION
    public List<TransactionDTO> getAllTxn(){
        List<TransactionModel> transactions = transactionRepository.findAll();
        return transactions.stream().map(transaction -> transactionConverter.convertDTO(transaction)).collect(Collectors.toList());
    }

    //GET BY ID
    public TxnWithDetailsDTO getTxnDetail(int transactionId){
        Optional<TransactionModel> txn = transactionRepository.findById(transactionId);

        if (txn.isEmpty()){
            return null;
        }else {
            return txnWithDetailsConverter.convertDTO(txn.get());
        }
    }
}
