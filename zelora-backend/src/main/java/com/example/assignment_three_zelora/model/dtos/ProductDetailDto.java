package com.example.assignment_three_zelora.model.dtos;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for the Product Detail Page (Assignment Spec Part 2)
 */
public class ProductDetailDto {

    private Integer productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private BigDecimal discountedPrice;
    private BigDecimal displayPrice;
    private String categoryName;
    private Double averageRating;
    private String featureImage;
    private List<ReviewDto> reviews;
    private String stockStatus;
    private String stockMessage;
    private Integer availableQuantity;

    public ProductDetailDto(Integer productId,
                            String productName,
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
                            Integer availableQuantity) {
        this.productId = productId;
        this.productName = productName;
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

    public String getProductName() {
        return productName;
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

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
