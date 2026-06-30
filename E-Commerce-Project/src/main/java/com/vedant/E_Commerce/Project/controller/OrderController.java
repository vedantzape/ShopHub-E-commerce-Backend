package com.vedant.E_Commerce.Project.controller;

import com.vedant.E_Commerce.Project.entity.Order;
import com.vedant.E_Commerce.Project.security.JwtUtil;
import com.vedant.E_Commerce.Project.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    private final OrderService service;
    private final JwtUtil jwtUtil;

    public OrderController(OrderService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/place")
    public Order placeOrder(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        return service.placeOrder(email);
    }

    @GetMapping
    public List<Order> getOrders(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        return service.getOrders(email);
    }
}