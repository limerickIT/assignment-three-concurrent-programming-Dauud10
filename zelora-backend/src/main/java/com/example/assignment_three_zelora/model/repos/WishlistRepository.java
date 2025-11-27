package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    // All wishlist rows for a customer
    List<Wishlist> findByCustomerId_CustomerId(Integer customerId);

    // Check if specific product is in wishlist
    boolean existsByCustomerId_CustomerIdAndProductId_ProductId(Integer customerId, Integer productId);

    // Remove specific product from wishlist
    void deleteByCustomerId_CustomerIdAndProductId_ProductId(Integer customerId, Integer productId);

    // For generating the next wishlist_id (since there is no AUTO_INCREMENT)
    Wishlist findTopByOrderByWishlistIdDesc();
}
