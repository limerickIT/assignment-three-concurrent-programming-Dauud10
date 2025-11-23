package com.example.assignment_three_zelora;

import com.example.assignment_three_zelora.model.dtos.ProductSummaryDto;
import com.example.assignment_three_zelora.model.dtos.ProductDetailDto;
import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // -------------------------
    // SEARCH ENDPOINT
    // -------------------------
    @GetMapping("/search")
    public List<ProductSummaryDto> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false, defaultValue = "false") boolean recentOnly
    ) {

        Date recentDate = null;
        if (recentOnly) {
            LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
            recentDate = Date.from(sevenDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        List<Product> results = productService.searchProducts(
                emptyToNull(name),
                emptyToNull(category),
                minPrice,
                maxPrice,
                recentOnly,
                emptyToNull(keywords)
        );

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

    private String emptyToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    // -------------------------
    // PRODUCT DETAIL ENDPOINT
    // -------------------------
    @GetMapping("/{id}/detail")
    public ResponseEntity<ProductDetailDto> getProductDetail(@PathVariable Integer id) {
        ProductDetailDto detail = productService.getProductDetail(id);

        if (detail == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(detail);
    }
}
