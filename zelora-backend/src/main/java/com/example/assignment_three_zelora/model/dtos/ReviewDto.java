package com.example.assignment_three_zelora.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 * A single review shown on the product detail page.
 */
public class ReviewDto {

    @JsonProperty("rating")
    private Integer rating;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("customer_first_name")
    private String customerFirstName;

    @JsonProperty("customer_city")
    private String customerCity;

    @JsonProperty("review_date")
    private Date reviewDate;

    public ReviewDto(Integer rating, String comment,
                     String customerFirstName, String customerCity,
                     Date reviewDate) {
        this.rating = rating;
        this.comment = comment;
        this.customerFirstName = customerFirstName;
        this.customerCity = customerCity;
        this.reviewDate = reviewDate;
    }

    public ReviewDto() {}

    public Integer getRating() { return rating; }
    public String getComment() { return comment; }
    public String getCustomerFirstName() { return customerFirstName; }
    public String getCustomerCity() { return customerCity; }
    public Date getReviewDate() { return reviewDate; }

    public void setRating(Integer rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setCustomerFirstName(String customerFirstName) { this.customerFirstName = customerFirstName; }
    public void setCustomerCity(String customerCity) { this.customerCity = customerCity; }
    public void setReviewDate(Date reviewDate) { this.reviewDate = reviewDate; }
}
