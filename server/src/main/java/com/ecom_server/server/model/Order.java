package com.ecom_server.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ecom_server.server.model.submodel.AddressInfo_orders;
import com.ecom_server.server.model.submodel.CartItem_orders;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Order {

    @Id
    @JsonProperty("_id")
    private String id;

    private String userId;
    private String cartId;

    private List<CartItem_orders> cartItems;

    private AddressInfo_orders addressInfo;

    private String orderStatus;
    private String paymentMethod;
    private String paymentStatus;

    private Double totalAmount;

    private Date orderDate;
    private Date orderUpdateDate;

    private String paymentId;
    private String payerId;
}
