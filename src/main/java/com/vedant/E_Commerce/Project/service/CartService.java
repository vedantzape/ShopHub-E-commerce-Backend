package com.vedant.E_Commerce.Project.service;

import com.vedant.E_Commerce.Project.entity.CartItem;
import com.vedant.E_Commerce.Project.entity.Product;
import com.vedant.E_Commerce.Project.entity.User;
import com.vedant.E_Commerce.Project.repository.CartRepository;
import com.vedant.E_Commerce.Project.repository.ProductRepository;
import com.vedant.E_Commerce.Project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    public CartService(CartRepository cartRepo,
                       UserRepository userRepo,
                       ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    public CartItem addToCart(Long userId, Long productId, int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existing = cartRepo.findByUserAndProduct(user, product);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return cartRepo.save(existing);
        }

        CartItem item = new CartItem();
        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(quantity);

        return cartRepo.save(item);
    }

    public List<CartItem> getCart(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepo.findByUser(user);
    }

    public void removeItem(Long id) {
        cartRepo.deleteById(id);
    }

    public CartItem increaseQuantity(Long id) {

        CartItem item = cartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart Item Not Found"));

        item.setQuantity(item.getQuantity() + 1);

        return cartRepo.save(item);
    }

    public CartItem decreaseQuantity(Long id) {

        CartItem item = cartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart Item Not Found"));

        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            return cartRepo.save(item);
        }

        cartRepo.delete(item);
        return item;
    }
}