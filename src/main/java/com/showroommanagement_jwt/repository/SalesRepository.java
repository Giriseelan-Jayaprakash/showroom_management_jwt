package com.showroommanagement_jwt.repository;

import com.showroommanagement_jwt.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {
    @Query("SELECT s FROM Sales s " +
            "JOIN s.bike b " +
            "JOIN b.salesman sa " +
            "JOIN sa.showroom sh " +
            "Join sh.salesManager smngr " +
            "JOIN s.customer cs " +
            "JOIN cs.salesman sman " +
            "JOIN sman.showroom sroom " +
            "WHERE sh.name = :showroomName " +
            "AND b.name = :bikeName")
    List<Sales> retrieveSalesByShowroomAndBikeName(String showroomName, String bikeName);

}
