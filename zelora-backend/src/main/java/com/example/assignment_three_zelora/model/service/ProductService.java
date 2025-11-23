package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.repos.ProductRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;


import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //create
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    //Get all
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //get one
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    //Update
    public Product updateProduct(Integer id, Product updatedProduct) {
        if (!productRepository.existsById(id)) {
            return null;
        }
        updatedProduct.setProductId(id);
        return productRepository.save(updatedProduct);
    }

    //Delete by id
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    // SEARCH products with filters
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

}
