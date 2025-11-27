package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.dtos.ProductDetailDto;
import com.example.assignment_three_zelora.model.dtos.ReviewDto;
import com.example.assignment_three_zelora.model.entitys.Inventory;
import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.entitys.Review;
import com.example.assignment_three_zelora.model.repos.InventoryRepository;
import com.example.assignment_three_zelora.model.repos.ProductRepository;
import com.example.assignment_three_zelora.model.repos.ReviewRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final InventoryRepository inventoryRepository;

    public ProductService(ProductRepository productRepository,
                          ReviewRepository reviewRepository,
                          InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.inventoryRepository = inventoryRepository;
    }

    // ------------------------------
    // CRUD
    // ------------------------------

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(Integer id, Product updatedProduct) {
        if (!productRepository.existsById(id)) {
            return null;
        }
        updatedProduct.setProductId(id);
        return productRepository.save(updatedProduct);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    // ------------------------------
    // SEARCH (your existing logic)
    // ------------------------------

    public List<Product> searchProducts(
            String name,
            String categoryName,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean recent,
            String keyword
    ) {

        Date recentDate = null;

        if (recent != null && recent) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -7);
            recentDate = cal.getTime();
        }

        return productRepository.searchProducts(
                name,
                categoryName,
                minPrice,
                maxPrice,
                recentDate,
                keyword
        );
    }

    // ------------------------------
    // PRODUCT DETAIL (required by spec)
    // ------------------------------

    public ProductDetailDto getProductDetail(Integer productId) {

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return null;

        // 1) Average rating
        Double averageRating = reviewRepository.findAverageRatingForProduct(productId);
        if (averageRating == null) averageRating = 0.0;

        // 2) Visible reviews (rating >=3, not spam)
        List<Review> reviews = reviewRepository.findVisibleReviewsByProduct(productId);

        List<ReviewDto> reviewDtos =
                reviews.stream().map(review ->
                        new ReviewDto(
                                review.getRating(),
                                review.getReviewText(),
                                // adjust if your field differs
                                review.getCustomerId().getFirstName(),
                                review.getCustomerId().getCity(),
                                review.getReviewDate()
                        )
                ).toList();

        // 3) Inventory aggregation: in stock vs low vs out
        List<Inventory> inventoryList = inventoryRepository.findByProductId(productId);

        int totalStock = 0;
        int reserved = 0;
        int reorderPoint = 0;

        for (Inventory inv : inventoryList) {
            totalStock += inv.getQuantityInStock();
            reserved += inv.getQuantityReserved();
            reorderPoint = Math.max(reorderPoint, inv.getReorderPoint());
        }

        int available = totalStock - reserved;

        String stockStatus;
        String stockMessage;

        if (available <= 0) {
            stockStatus = "OUT_OF_STOCK";
            stockMessage = "Out of stock";
        } else if (available <= reorderPoint) {
            stockStatus = "LOW_STOCK";
            stockMessage = "Low stock — only " + available + " left!";
        } else {
            stockStatus = "IN_STOCK";
            stockMessage = "In stock (" + available + " available)";
        }

        // 4) Effective display price
        BigDecimal price = product.getPrice();
        BigDecimal discounted = product.getDiscountedPrice();

        BigDecimal displayPrice = (discounted != null && discounted.compareTo(price) < 0)
                ? discounted
                : price;

        // 5) Category name
        String categoryName = product.getCategoryId() != null
                ? product.getCategoryId().getCategoryName()
                : null;

        // 6) Build DTO
        return new ProductDetailDto(
                product.getProductId(),
                product.getProductName(),
                product.getDescription(),
                price,
                discounted,
                displayPrice,
                categoryName,
                averageRating,
                product.getFeatureImage(),
                reviewDtos,
                stockStatus,
                stockMessage,
                available
        );
    }
    // ------------------------------
    // RECOMMENDED PRODUCTS (unique feature)
    // ------------------------------
    public List<Product> getRecommendedProducts(Integer productId) {

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return List.of();
        }

        Set<Product> recommended = new LinkedHashSet<>();

        // 1) Same category
        if (product.getCategoryId() != null) {
            recommended.addAll(
                    productRepository.findByCategoryForRecommendations(
                            product.getCategoryId().getCategoryId(),
                            productId
                    )
            );
        }

        // 2) Similar colour
        if (product.getColour() != null && !product.getColour().isBlank()) {
            recommended.addAll(
                    productRepository.findByColourForRecommendations(
                            product.getColour(),
                            productId
                    )
            );
        }

        // 3) Similar material
        if (product.getMaterial() != null && !product.getMaterial().isBlank()) {
            recommended.addAll(
                    productRepository.findByMaterialForRecommendations(
                            product.getMaterial(),
                            productId
                    )
            );
        }

        // 4) Similar sustainability rating
        if (product.getSustainabilityRating() != null) {
            recommended.addAll(
                    productRepository.findBySustainabilityForRecommendations(
                            product.getSustainabilityRating(),
                            productId
                    )
            );
        }

        // Keep it small for UI (e.g. max 8)
        return recommended.stream().limit(8).toList();
    }

}
