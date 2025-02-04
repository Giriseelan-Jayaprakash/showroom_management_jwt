package com.showroommanagement_jwt.repository;

import com.showroommanagement_jwt.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, String> {
    @Query("SELECT s FROM Sales s " +
            "JOIN s.bike b " +
            "JOIN b.salesman sa " +
            "Join sa.salesManager smngr " +
            "JOIN smngr.showroom sh " +
            "JOIN s.customer cs " +
            "JOIN cs.salesman sman " +
            "Join sa.salesManager smanager " +
            "JOIN smanager.showroom sroom " +
            "WHERE sh.name = :showroomName " +
            "AND b.name = :bikeName")
    List<Sales> retrieveSalesByShowroomAndBikeName(String showroomName, String bikeName);

}
