package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    List<Wishlist> findByCustomerId_CustomerId(Integer customerId);

    boolean existsByCustomerId_CustomerIdAndProductId_ProductId(Integer customerId, Integer productId);

    void deleteByCustomerId_CustomerIdAndProductId_ProductId(Integer customerId, Integer productId);
}
