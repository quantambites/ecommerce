package com.ecom_server.server.model.submodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    private String productId;
    private String image;
    private String title;
    private double price;
    private double salePrice;
    private int quantity;
}
