package com.ecom_server.server.model.submodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class OrderRequest {

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
