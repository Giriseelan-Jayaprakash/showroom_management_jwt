package com.showroommanagement_jwt.repository;

import com.showroommanagement_jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmailId(String emailId);

    boolean existsByEmailId(String emailId);
}