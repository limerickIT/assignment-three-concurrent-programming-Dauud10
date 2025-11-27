package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.dtos.ProductSummaryDto;
import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.entitys.Wishlist;
import com.example.assignment_three_zelora.model.repos.ProductRepository;
import com.example.assignment_three_zelora.model.repos.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository,
                           ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    // customerId is ignored (dummy user 1 for this assignment)
    public boolean isInWishlist(Integer customerId, Integer productId) {
        return wishlistRepository.existsByProductId_ProductId(productId);
    }

    public void addToWishlist(Integer customerId, Integer productId) {

        if (isInWishlist(customerId, productId)) {
            return;
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found: " + productId));

        Wishlist last = wishlistRepository.findTopByOrderByWishlistIdDesc();
        int nextId = (last != null ? last.getWishlistId() + 1 : 1);

        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(nextId);
        wishlist.setProductId(product);
        wishlist.setAddedDate(new Date());
        wishlist.setWishlistName("Default");
        wishlist.setNotes(null);
        // leave customerId as null so we don't need Customer repo

        wishlistRepository.save(wishlist);
    }

    public void removeFromWishlist(Integer customerId, Integer productId) {
        wishlistRepository.deleteByProductId_ProductId(productId);
    }

    public List<ProductSummaryDto> getWishlistForCustomer(Integer customerId) {
        List<Wishlist> rows = wishlistRepository.findAll();

        return rows.stream()
                .map(Wishlist::getProductId)
                .map(p -> new ProductSummaryDto(
                        p.getProductId(),
                        p.getProductName(),
                        p.getPrice(),
                        p.getDiscountedPrice(),
                        p.getFeatureImage(),
                        p.getCategoryId() != null ? p.getCategoryId().getCategoryName() : null,
                        p.getReleaseDate()
                ))
                .collect(Collectors.toList());
    }
}
