package com.example.assignment_three_zelora.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Simple data transfer object for search results.
 * We only expose the fields the frontend actually needs.
 */
public class ProductSummaryDto {

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("discounted_price")
    private BigDecimal discountedPrice;

    @JsonProperty("feature_image")
    private String featureImage;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("release_date")
    private Date releaseDate;

    public ProductSummaryDto(Integer productId,
                             String productName,
                             BigDecimal price,
                             BigDecimal discountedPrice,
                             String featureImage,
                             String categoryName,
                             Date releaseDate) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.featureImage = featureImage;
        this.categoryName = categoryName;
        this.releaseDate = releaseDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public String getFeatureImage() {
        return featureImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }
}
