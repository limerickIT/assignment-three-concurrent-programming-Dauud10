package com.example.assignment_three_zelora.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class WishlistItemDto {

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("display_price")
    private BigDecimal displayPrice;

    @JsonProperty("feature_image")
    private String featureImage;

    @JsonProperty("added_date")
    private Date addedDate;

    @JsonProperty("wishlist_name")
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

    public WishlistItemDto() {}

    public Integer getProductId() { return productId; }
    public String getProductName() { return productName; }
    public BigDecimal getDisplayPrice() { return displayPrice; }
    public String getFeatureImage() { return featureImage; }
    public Date getAddedDate() { return addedDate; }
    public String getWishlistName() { return wishlistName; }

    public void setProductId(Integer productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setDisplayPrice(BigDecimal displayPrice) { this.displayPrice = displayPrice; }
    public void setFeatureImage(String featureImage) { this.featureImage = featureImage; }
    public void setAddedDate(Date addedDate) { this.addedDate = addedDate; }
    public void setWishlistName(String wishlistName) { this.wishlistName = wishlistName; }
}
