package com.example.assignment_three_zelora.model.controllers;

import com.example.assignment_three_zelora.model.dtos.ProductSummaryDto;
import com.example.assignment_three_zelora.model.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // Get all wishlist items
    @GetMapping("/customer/{customerId}")
    public List<ProductSummaryDto> getWishlist(@PathVariable Integer customerId) {
        return wishlistService.getWishlistForCustomer(customerId);
    }

    // Check if product is in wishlist
    @GetMapping("/customer/{customerId}/contains/{productId}")
    public boolean isInWishlist(@PathVariable Integer customerId,
                                @PathVariable Integer productId) {
        return wishlistService.isInWishlist(customerId, productId);
    }

    // Add to wishlist
    @PostMapping("/customer/{customerId}/add/{productId}")
    public ResponseEntity<Void> addToWishlist(@PathVariable Integer customerId,
                                              @PathVariable Integer productId,
                                              @RequestParam(required = false) String name) {
        wishlistService.addToWishlist(customerId, productId, name);
        return ResponseEntity.ok().build();
    }

    // Remove from wishlist
    @DeleteMapping("/customer/{customerId}/remove/{productId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Integer customerId,
                                                   @PathVariable Integer productId) {
        wishlistService.removeFromWishlist(customerId, productId);
        return ResponseEntity.noContent().build();
    }
}
