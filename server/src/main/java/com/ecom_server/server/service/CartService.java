package com.ecom_server.server.service;

import com.ecom_server.server.model.Cart;
import com.ecom_server.server.model.Product;
import com.ecom_server.server.model.submodel.CartItemResponse;
import com.ecom_server.server.model.submodel.CartItem_carts;
import com.ecom_server.server.model.submodel.CartResponse;
import com.ecom_server.server.repository.CartRepository;
import com.ecom_server.server.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addToCart(String userId, String productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(null, userId, new ArrayList<>(), null, null));

        List<CartItem_carts> items = cart.getItems();

        Optional<CartItem_carts> existingItem = items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem_carts item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            items.add(new CartItem_carts(productId, quantity));
        }

        cart.setItems(items);
        return cartRepository.save(cart);
    }

    public CartResponse fetchCartItems(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(null, userId, new ArrayList<>(), null, null));

        List<CartItemResponse> responseItems = new ArrayList<>();

        Iterator<CartItem_carts> iterator = cart.getItems().iterator();

        while (iterator.hasNext()) {
            CartItem_carts item = iterator.next();

            Optional<Product> optionalProduct = productRepository.findById(item.getProductId());
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                responseItems.add(new CartItemResponse(
                        product.getId(),
                        product.getImage(),
                        product.getTitle(),
                        product.getPrice(),
                        product.getSalePrice(),
                        item.getQuantity()
                ));
            } else {
                // Remove invalid productId reference
                iterator.remove();
            }
        }

        // Save updated cart if invalid items were removed
        cart.setItems(cart.getItems());
        cartRepository.save(cart);

        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                responseItems,
                cart.getCreatedAt(),
                cart.getUpdatedAt()
        );
    }


    public CartResponse updateCartItemQuantity(String userId, String productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        boolean updated = false;

        for (CartItem_carts item : cart.getItems()) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new NoSuchElementException("Cart item not found");
        }

        Cart updatedCart = cartRepository.save(cart);
        return buildCartResponse(updatedCart);
    }


    public CartResponse deleteCartItem(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        List<CartItem_carts> updatedItems = cart.getItems().stream()
                .filter(item -> !item.getProductId().equals(productId))
                .collect(Collectors.toList());

        cart.setItems(updatedItems);
        Cart updatedCart = cartRepository.save(cart);

        return buildCartResponse(updatedCart);
    }

    private CartResponse buildCartResponse(Cart cart) {
        List<CartItemResponse> enrichedItems = new ArrayList<>();

        for (CartItem_carts item : cart.getItems()) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);

            if (product != null) {
                enrichedItems.add(CartItemResponse.builder()
                        .productId(product.getId())
                        .image(product.getImage())
                        .title(product.getTitle())
                        .price(product.getPrice())
                        .salePrice(product.getSalePrice())
                        .quantity(item.getQuantity())
                        .build());
            }
        }

        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .items(enrichedItems)
                .build();
    }


}
