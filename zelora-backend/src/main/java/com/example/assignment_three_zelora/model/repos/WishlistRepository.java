package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    // We treat wishlist as “global” (dummy customer) for now
    boolean existsByProductId_ProductId(Integer productId);

    void deleteByProductId_ProductId(Integer productId);

    List<Wishlist> findAll();

    Wishlist findTopByOrderByWishlistIdDesc();
}
