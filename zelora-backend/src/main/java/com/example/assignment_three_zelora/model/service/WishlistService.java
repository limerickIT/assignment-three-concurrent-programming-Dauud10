package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.dtos.ProductSummaryDto;
import com.example.assignment_three_zelora.model.entitys.Customer;
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

    public boolean isInWishlist(Integer customerId, Integer productId) {
        return wishlistRepository
                .existsByCustomerId_CustomerIdAndProductId_ProductId(customerId, productId);
    }

    public void addToWishlist(Integer customerId, Integer productId, String wishlistName) {

        // If already exists, skip
        if (isInWishlist(customerId, productId)) return;

        // Get product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        // Create dummy customer (no need for CustomerRepository)
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        // Manual ID generation (your DB has no auto increment)
        Wishlist last = wishlistRepository.findTopByOrderByWishlistIdDesc();
        int nextId = (last != null ? last.getWishlistId() + 1 : 1);

        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(nextId);
        wishlist.setCustomerId(customer);
        wishlist.setProductId(product);
        wishlist.setAddedDate(new Date());
        wishlist.setWishlistName(wishlistName != null ? wishlistName : "Default");
        wishlist.setNotes(null);

        wishlistRepository.save(wishlist);
    }

    public void removeFromWishlist(Integer customerId, Integer productId) {
        wishlistRepository
                .deleteByCustomerId_CustomerIdAndProductId_ProductId(customerId, productId);
    }

    public List<ProductSummaryDto> getWishlistForCustomer(Integer customerId) {
        List<Wishlist> rows = wishlistRepository.findByCustomerId_CustomerId(customerId);

        return rows.stream()
                .map(w -> w.getProductId())
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
