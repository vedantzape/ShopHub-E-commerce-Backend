package com.vedant.E_Commerce.Project.service;

import com.vedant.E_Commerce.Project.entity.Product;
import com.vedant.E_Commerce.Project.repository.CartRepository;
import com.vedant.E_Commerce.Project.repository.OrderRepository;
import com.vedant.E_Commerce.Project.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final CartRepository cartRepo;
    private final OrderRepository orderItemRepo;

    public ProductService(ProductRepository repo,
                          CartRepository cartRepo,
                          OrderRepository orderItemRepo) {
        this.repo = repo;
        this.cartRepo = cartRepo;
        this.orderItemRepo = orderItemRepo;
    }

    public Product addProduct(Product product) {
        return repo.save(product);
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with ID " + id));
    }

    public List<Product> searchProducts(String keyword) {
        return repo.findByNameContainingIgnoreCase(keyword);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with ID " + id));

        // Step 1: Delete cart items referencing this product
        cartRepo.deleteByProduct(product);

        // Step 2: Delete order items referencing this product
        orderItemRepo.deleteByProduct(product);

        // Step 3: Now safe to delete the product
        repo.deleteById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());
        product.setImageUrl(updatedProduct.getImageUrl());

        return repo.save(product);
    }
}