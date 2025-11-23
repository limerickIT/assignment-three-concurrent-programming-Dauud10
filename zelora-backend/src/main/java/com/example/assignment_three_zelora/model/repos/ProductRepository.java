package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Search products using multiple optional filters.
     * Any parameter can be null, and that filter will be ignored.
     */
    @Query("""
            SELECT p
            FROM Product p
            LEFT JOIN p.categoryId c
            WHERE (:name IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:categoryName IS NULL OR LOWER(c.categoryName) = LOWER(:categoryName))
              AND (:minPrice IS NULL OR p.price >= :minPrice)
              AND (:maxPrice IS NULL OR p.price <= :maxPrice)
              AND (:recentDate IS NULL OR p.releaseDate >= :recentDate)
              AND (:keyword IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    List<Product> searchProducts(
            @Param("name") String name,
            @Param("categoryName") String categoryName,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("recentDate") Date recentDate,
            @Param("keyword") String keyword
    );
}
