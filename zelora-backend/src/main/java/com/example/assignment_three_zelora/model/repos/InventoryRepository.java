package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    @Query("""
           SELECT i
           FROM Inventory i
           WHERE i.productId.productId = :productId
           """)
    List<Inventory> findByProductId(@Param("productId") Integer productId);
}
