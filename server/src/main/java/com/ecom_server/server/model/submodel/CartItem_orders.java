package com.ecom_server.server.model.submodel;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CartItem_orders {

    private String productId;
    private String title;
    private String image;
    private String price;
    private int quantity;
}
