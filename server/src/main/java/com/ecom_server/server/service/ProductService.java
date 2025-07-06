package com.ecom_server.server.service;

import com.ecom_server.server.model.Product;
import com.ecom_server.server.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product addProduct(Product product) {
        product.setAverageReview(0.0);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product editProduct(String id, Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found  " + id);
        }

        Product product = optionalProduct.get();
        product.setTitle(updatedProduct.getTitle());
        product.setDescription(updatedProduct.getDescription());
        product.setCategory(updatedProduct.getCategory());
        product.setBrand(updatedProduct.getBrand());
        product.setPrice(updatedProduct.getPrice());
        product.setSalePrice(updatedProduct.getSalePrice());
        product.setTotalStock(updatedProduct.getTotalStock());
        product.setImage(updatedProduct.getImage());
        product.setAverageReview(updatedProduct.getAverageReview());

        return productRepository.save(product);
    }

    public String deleteProduct(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
        return "Product delete successfully";
    }
    public List<Product> getFilteredProducts(List<String> category, List<String> brand, String sortBy) {
        List<Product> products = productRepository.findAll();

        if (category != null && !category.isEmpty()) {
            products = products.stream()
                    .filter(p -> category.contains(p.getCategory()))
                    .collect(Collectors.toList());
        }

        if (brand != null && !brand.isEmpty()) {
            products = products.stream()
                    .filter(p -> brand.contains(p.getBrand()))
                    .collect(Collectors.toList());
        }

        Comparator<Product> comparator = Comparator.comparing(Product::getPrice);

        switch (sortBy) {
            case "price-hightolow":
                comparator = comparator.reversed();
                break;
            case "title-atoz":
                comparator = Comparator.comparing(Product::getTitle);
                break;
            case "title-ztoa":
                comparator = Comparator.comparing(Product::getTitle).reversed();
                break;
        }

        return products.stream().sorted(comparator).collect(Collectors.toList());
    }

    // 🔍 Product by ID
    public Product getProductDetails(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
    }
}
