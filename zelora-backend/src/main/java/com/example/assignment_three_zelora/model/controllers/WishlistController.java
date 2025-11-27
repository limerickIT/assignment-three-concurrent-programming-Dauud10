package com.example.assignment_three_zelora.model.controllers;

import com.example.assignment_three_zelora.model.dtos.ProductSummaryDto;
import com.example.assignment_three_zelora.model.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "http://localhost:5175") // adjust port if needed
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/customer/{customerId}")
    public List<ProductSummaryDto> getWishlist(@PathVariable Integer customerId) {
        return wishlistService.getWishlistForCustomer(customerId);
    }

    @GetMapping("/customer/{customerId}/contains/{productId}")
    public boolean isInWishlist(@PathVariable Integer customerId,
                                @PathVariable Integer productId) {
        return wishlistService.isInWishlist(customerId, productId);
    }

    @PostMapping("/customer/{customerId}/add/{productId}")
    public ResponseEntity<Void> addToWishlist(@PathVariable Integer customerId,
                                              @PathVariable Integer productId) {
        wishlistService.addToWishlist(customerId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/customer/{customerId}/remove/{productId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Integer customerId,
                                                   @PathVariable Integer productId) {
        wishlistService.removeFromWishlist(customerId, productId);
        return ResponseEntity.noContent().build();
    }
}
