package com.example.assignment_three_zelora.web;

import com.example.assignment_three_zelora.model.dtos.ProductSummaryDto;
import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.repos.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for product-related endpoints.
 * First endpoint: /api/products/search
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/search")
    public List<ProductSummaryDto> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false, defaultValue = "false") boolean recentOnly
    ) {

        // calculate date for "last 7 days" if recentOnly = true
        Date recentDate = null;
        if (recentOnly) {
            LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
            recentDate = Date.from(sevenDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        // call repository search method
        List<Product> results = productRepository.searchProducts(
                emptyToNull(name),
                emptyToNull(category),
                minPrice,
                maxPrice,
                recentDate,
                emptyToNull(keywords)
        );

        // map entities to DTOs for the frontend
        return results.stream()
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

    /**
     * Helper: treat empty strings as null so filters are ignored.
     */
    private String emptyToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}
