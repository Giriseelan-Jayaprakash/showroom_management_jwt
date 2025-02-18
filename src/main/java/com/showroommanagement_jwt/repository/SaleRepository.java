package com.showroommanagement_jwt.repository;

import com.showroommanagement_jwt.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {
    @Query("SELECT s FROM Sale s " +
            "JOIN s.bike b " +
            "JOIN b.salesman sa " +
            "JOIN sa.salesManager sm " +
            "JOIN sm.showroom sh " +
            "WHERE sh.name = :showroomName " +
            "AND b.name = :bikeName")
    List<Sale> retrieveSaleByShowroomAndBikeName( @Param("showroomName") String showroomName, @Param("bikeName") String bikeName);
}

