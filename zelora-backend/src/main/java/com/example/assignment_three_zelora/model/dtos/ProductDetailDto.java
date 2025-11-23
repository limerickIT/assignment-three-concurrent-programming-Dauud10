package com.example.assignment_three_zelora.model.dtos;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO sent to the frontend for the product drill-down page.
 * Combines product info, category, rating, reviews and stock status.
 */
public class ProductDetailDto {

    private Integer productId;
    private String name;
    private String description;

    // original prices from DB
    private BigDecimal price;
    private BigDecimal discountedPrice;

    // effective price the UI should actually display
    private BigDecimal displayPrice;

    private String categoryName;
    private Double averageRating;

    // for now we’ll just use featureImage – you can extend to a list later
    private String featureImage;

    private List<ReviewDto> reviews;

    // stock info calculated from inventory
    private String stockStatus;    // IN_STOCK / LOW_STOCK / OUT_OF_STOCK
    private String stockMessage;   // "In stock (X available)" / "Low stock..." etc.
    private int availableQuantity;

    public ProductDetailDto(Integer productId,
                            String name,
                            String description,
                            BigDecimal price,
                            BigDecimal discountedPrice,
                            BigDecimal displayPrice,
                            String categoryName,
                            Double averageRating,
                            String featureImage,
                            List<ReviewDto> reviews,
                            String stockStatus,
                            String stockMessage,
                            int availableQuantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.displayPrice = displayPrice;
        this.categoryName = categoryName;
        this.averageRating = averageRating;
        this.featureImage = featureImage;
        this.reviews = reviews;
        this.stockStatus = stockStatus;
        this.stockMessage = stockMessage;
        this.availableQuantity = availableQuantity;
    }

    public ProductDetailDto() {
    }

    public Integer getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public BigDecimal getDisplayPrice() {
        return displayPrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public String getFeatureImage() {
        return featureImage;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public String getStockMessage() {
        return stockMessage;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public void setDisplayPrice(BigDecimal displayPrice) {
        this.displayPrice = displayPrice;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setFeatureImage(String featureImage) {
        this.featureImage = featureImage;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public void setStockMessage(String stockMessage) {
        this.stockMessage = stockMessage;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
