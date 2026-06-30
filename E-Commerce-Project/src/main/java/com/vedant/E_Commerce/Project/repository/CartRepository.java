package com.vedant.E_Commerce.Project.repository;

import com.vedant.E_Commerce.Project.entity.CartItem;
import com.vedant.E_Commerce.Project.entity.Product;
import com.vedant.E_Commerce.Project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByProduct(Product product);
}