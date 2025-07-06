package com.ecom_server.server.controller;

import com.ecom_server.server.model.Product;
import com.ecom_server.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ➕ Add product
    @PostMapping("/admin/products/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try{
            Product created = productService.addProduct(product);
            return ResponseEntity.status(201).body(Map.of(
                    "success", true,
                    "data", created
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }

    // 📥 Get all products (admin)
    @GetMapping("/admin/products/get")
    public ResponseEntity<?> getAllProducts() {
        try{
            List<Product> listOfProducts = productService.getAllProducts();
            //listOfProducts.forEach(p -> System.out.println(p)); // Log for debugging
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", listOfProducts
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }

    // ✏️ Edit product
    @PutMapping("/admin/products/edit/{id}")
    public ResponseEntity<?> editProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        try{
            Product edited = productService.editProduct(id, updatedProduct);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", edited
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }

    // ❌ Delete product
    @DeleteMapping("/admin/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try{
            String message = productService.deleteProduct(id);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", message
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }

    // 🛍️ Filtered products (shop)
    @GetMapping("/shop/products/get")
    public ResponseEntity<?> getFilteredProducts(
            @RequestParam(required = false) List<String> category,
            @RequestParam(required = false) List<String> brand,
            @RequestParam(defaultValue = "price-lowtohigh") String sortBy
    ) {
        try{
            List<Product> filtered = productService.getFilteredProducts(category, brand, sortBy);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", filtered
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }

    // 🔍 Product details
    @GetMapping("/shop/products/get/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable String id) {
        try{
            Product product = productService.getProductDetails(id);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", product
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }
}
