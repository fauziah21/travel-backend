package com.fauzia.project.backend.repository;

import com.fauzia.project.backend.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {
}
