package com.showroommanagement_jwt.repository;

import com.showroommanagement_jwt.entity.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowroomRepository extends JpaRepository<Showroom, String> {
}
