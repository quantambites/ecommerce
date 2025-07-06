package com.ecom_server.server.controller;

import com.ecom_server.server.model.Cart;
import com.ecom_server.server.model.submodel.CartResponse;
import com.ecom_server.server.service.CartService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/shop/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest request) {
        try{
            Cart cart = cartService.addToCart(request.getUserId(), request.getProductId(), request.getQuantity());
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data",  cart
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getCartItems(@PathVariable String userId) {
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "User id is mandatory!"
            ));
        }

        CartResponse cartResponse = cartService.fetchCartItems(userId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", cartResponse
        ));
    }

    @PutMapping("/update-cart")
    public ResponseEntity<?> updateCartItem(@RequestBody CartRequest request) {
        try{
            CartResponse cartResponse = cartService.updateCartItemQuantity(
                    request.getUserId(), request.getProductId(), request.getQuantity());
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", cartResponse
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable String userId, @PathVariable String productId) {
        try{
            CartResponse cartResponse = cartService.deleteCartItem(userId, productId);
            return ResponseEntity.ok(Map.of("success", true, "data", cartResponse));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }


    @Data
    public static class CartRequest {
        private String userId;
        private String productId;
        private int quantity;
    }
}
