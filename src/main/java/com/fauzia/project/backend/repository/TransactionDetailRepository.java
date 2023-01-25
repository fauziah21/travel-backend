package com.fauzia.project.backend.repository;

import com.fauzia.project.backend.model.TransactionDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetailModel, Integer> {
    List<TransactionDetailModel> findByTransactionId(int transactionId);
}
