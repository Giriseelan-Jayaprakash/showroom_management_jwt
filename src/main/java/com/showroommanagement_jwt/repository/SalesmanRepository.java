package com.showroommanagement_jwt.repository;

import com.showroommanagement_jwt.entity.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesmanRepository extends JpaRepository<Salesman, String> {
}
