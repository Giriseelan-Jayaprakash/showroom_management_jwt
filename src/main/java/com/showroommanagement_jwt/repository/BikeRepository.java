package com.showroommanagement_jwt.repository;

import com.showroommanagement_jwt.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<Bike, String> {
}
