package com.fauzia.project.backend.repository;

import com.fauzia.project.backend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUserEmail(String userEmail);

    Optional<UserModel> findByUserName(String userName);
}
