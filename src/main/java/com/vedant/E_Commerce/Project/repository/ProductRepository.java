package com.vedant.E_Commerce.Project.repository;

import com.vedant.E_Commerce.Project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(
            String keyword
    );
}
