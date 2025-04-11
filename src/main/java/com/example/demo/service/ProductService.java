package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductImage;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Fetches products based on category name. If category is null or empty, fetches all products.
     * @param categoryName The name of the category to filter products.
     * @return List of products belonging to the category.
     */
    public List<Product> getProductsByCategory(String categoryName) {
        if (categoryName != null && !categoryName.isBlank()) {
            Optional<Category> categoryOpt = categoryRepository.findByCategoryName(categoryName);
            if (categoryOpt.isPresent()) {
                return productRepository.findByCategory_CategoryId(categoryOpt.get().getCategoryId());
            } else {
                throw new RuntimeException("Category '" + categoryName + "' not found");
            }
        } else {
            return productRepository.findAll(); // Fetch all products if no category is provided
        }
    }

    /**
     * Fetches all products.
     * @return List of all products.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Fetches product images for a given product ID.
     * @param productId The ID of the product.
     * @return List of image URLs for the product.
     */
    public List<String> getProductImages(Integer productId) {
        List<ProductImage> productImages = productImageRepository.findByProduct_ProductId(productId);
        List<String> imageUrls = new ArrayList<>();
        for (ProductImage image : productImages) {
            imageUrls.add(image.getImageUrl());
        }
        return imageUrls;
    }
}
