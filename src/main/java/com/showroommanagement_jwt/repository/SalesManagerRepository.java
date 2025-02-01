package com.showroommanagement_jwt.repository;

import com.showroommanagement_jwt.entity.SalesManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesManagerRepository extends JpaRepository<SalesManager, Integer> {
}
