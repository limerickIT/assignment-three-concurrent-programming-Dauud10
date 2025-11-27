package com.example.assignment_three_zelora.model.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category implements Serializable {

    @Id
    @Basic(optional = false)
    @JsonProperty("categoryId") // JSON key
    @Column(name = "category_id") // DB column
    private Integer categoryId;

    @JsonProperty("categoryName")
    @Column(name = "category_name")
    private String categoryName;

    @JsonProperty("categoryImage")
    @Column(name = "category_image")
    private String categoryImage;

    // Prevents infinite recursion + huge payloads
    @JsonIgnore
    @OneToMany(mappedBy = "categoryId")
    private List<Product> productList;

    public Category(Integer categoryId, String categoryName, String categoryImage, List<Product> productList) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.productList = productList;
    }

    public Category() {}

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Category(categoryId=" + categoryId +
                ", categoryName=" + categoryName +
                ", categoryImage=" + categoryImage + ")";
    }
}
