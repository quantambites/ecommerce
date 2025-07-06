package com.ecom_server.server.model.submodel;

import lombok.Data;

@Data
public class CaptureRequest {
    private String paymentId;
    private String payerId;
    private String orderId;
}
