package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // ===========================
    // MAIN SEARCH QUERY (WORKING)
    // ===========================
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


    // ===========================
    // RECOMMENDATION QUERIES
    // ===========================

    // 1) Same category
    @Query("""
        SELECT p FROM Product p
        WHERE p.categoryId.categoryId = :categoryId
          AND p.productId <> :excludeProductId
        ORDER BY p.releaseDate DESC
        """)
    List<Product> findByCategoryForRecommendations(
            @Param("categoryId") Integer categoryId,
            @Param("excludeProductId") Integer excludeProductId
    );

    // 2) Same colour
    @Query("""
        SELECT p FROM Product p
        WHERE LOWER(p.colour) = LOWER(:colour)
          AND p.productId <> :excludeProductId
        """)
    List<Product> findByColourForRecommendations(
            @Param("colour") String colour,
            @Param("excludeProductId") Integer excludeProductId
    );

    // 3) Same material
    @Query("""
        SELECT p FROM Product p
        WHERE LOWER(p.material) = LOWER(:material)
          AND p.productId <> :excludeProductId
        """)
    List<Product> findByMaterialForRecommendations(
            @Param("material") String material,
            @Param("excludeProductId") Integer excludeProductId
    );

    // 4) Similar sustainability rating
    @Query("""
        SELECT p FROM Product p
        WHERE p.sustainabilityRating BETWEEN :rating - 1 AND :rating + 1
          AND p.productId <> :excludeProductId
        """)
    List<Product> findBySustainabilityForRecommendations(
            @Param("rating") Integer rating,
            @Param("excludeProductId") Integer excludeProductId
    );
}
