package com.showroommanagement_jwt.repository;

import com.showroommanagement_jwt.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {

    Optional<UserCredential> findByEmailId(String emailId);

    boolean existsByEmailId(String emailId);
}