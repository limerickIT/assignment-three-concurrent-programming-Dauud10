package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // Average rating for one product (spec: "Average rating for the product")
    @Query("""
           SELECT AVG(r.rating)
           FROM Review r
           WHERE r.productId.productId = :productId
           """)
    Double findAverageRatingForProduct(@Param("productId") Integer productId);

    // All non-spam reviews with rating >= 3 for that product
    @Query("""
           SELECT r
           FROM Review r
           WHERE r.productId.productId = :productId
             AND r.rating >= 3
             AND (r.flaggedAsSpam IS NULL OR r.flaggedAsSpam = false)
           """)
    List<Review> findVisibleReviewsByProduct(@Param("productId") Integer productId);
}
