package com.showroommanagement_jwt.repository;

import com.showroommanagement_jwt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    //    Users findByEmailId(String emailId);
    Optional<Users> findByEmailId(String emailId);

    boolean existsByEmailId(String emailId);
}