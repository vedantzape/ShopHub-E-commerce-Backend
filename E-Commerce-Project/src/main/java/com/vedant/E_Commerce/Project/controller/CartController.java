package com.vedant.E_Commerce.Project.controller;

import com.vedant.E_Commerce.Project.entity.CartItem;
import com.vedant.E_Commerce.Project.entity.User;
import com.vedant.E_Commerce.Project.security.JwtUtil;
import com.vedant.E_Commerce.Project.service.CartService;
import com.vedant.E_Commerce.Project.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    private final CartService service;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public CartController(CartService service,
                          JwtUtil jwtUtil,
                          UserService userService) {
        this.service = service;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/add")
    public CartItem addToCart(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long productId,
            @RequestParam int quantity) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        User user = userService.getUserByEmail(email);

        return service.addToCart(user.getId(), productId, quantity);
    }

    @GetMapping
    public List<CartItem> getCart(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        User user = userService.getUserByEmail(email);

        return service.getCart(user.getId());
    }

    @DeleteMapping("/{id}")
    public String removeItem(@PathVariable Long id) {
        service.removeItem(id);
        return "Item removed successfully";
    }

    @PutMapping("/increase/{id}")
    public CartItem increaseQuantity(@PathVariable Long id) {
        return service.increaseQuantity(id);
    }

    @PutMapping("/decrease/{id}")
    public CartItem decreaseQuantity(@PathVariable Long id) {
        return service.decreaseQuantity(id);
    }
}