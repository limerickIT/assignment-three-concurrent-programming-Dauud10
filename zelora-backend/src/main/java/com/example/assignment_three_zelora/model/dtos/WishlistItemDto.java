package com.example.assignment_three_zelora.model.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class WishlistItemDto {

    private Integer productId;
    private String productName;
    private BigDecimal displayPrice;
    private String featureImage;
    private Date addedDate;
    private String wishlistName;

    public WishlistItemDto(Integer productId,
                           String productName,
                           BigDecimal displayPrice,
                           String featureImage,
                           Date addedDate,
                           String wishlistName) {
        this.productId = productId;
        this.productName = productName;
        this.displayPrice = displayPrice;
        this.featureImage = featureImage;
        this.addedDate = addedDate;
        this.wishlistName = wishlistName;
    }

    public WishlistItemDto() {
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getDisplayPrice() {
        return displayPrice;
    }

    public String getFeatureImage() {
        return featureImage;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDisplayPrice(BigDecimal displayPrice) {
        this.displayPrice = displayPrice;
    }

    public void setFeatureImage(String featureImage) {
        this.featureImage = featureImage;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }
}
