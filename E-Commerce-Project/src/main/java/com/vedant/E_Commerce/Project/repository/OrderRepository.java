package com.vedant.E_Commerce.Project.repository;

import com.vedant.E_Commerce.Project.entity.Order;
import com.vedant.E_Commerce.Project.entity.Product;
import com.vedant.E_Commerce.Project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("DELETE FROM OrderItem oi WHERE oi.product = :product")
    void deleteByProduct(@Param("product") Product product);

    List<Order> findByUser(User user);
}