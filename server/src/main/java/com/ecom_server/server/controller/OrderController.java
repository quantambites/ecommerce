package com.ecom_server.server.controller;

import com.ecom_server.server.model.Order;
import com.ecom_server.server.model.submodel.CaptureRequest;
import com.ecom_server.server.model.submodel.OrderRequest;
import com.ecom_server.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/shop/order/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            Map<String, Object> result = orderService.createOrder(orderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "success", true,
                    "approvalURL", result.get("approvalURL"),
                    "orderId", result.get("orderId")
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Some error occurred!"
            ));
        }
    }

    @PostMapping("/shop/order/capture")
    public ResponseEntity<?> capturePayment(@RequestBody CaptureRequest captureRequest) {
        try {
            Order confirmedOrder = orderService.capturePayment(captureRequest);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Order confirmed",
                    "data", confirmedOrder
            ));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Some error occurred!"
            ));
        }
    }

    @GetMapping("/shop/order/list/{userId}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable String userId) {
        List<Order> orders = orderService.getAllOrdersByUser(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", false,
                    "message", "No orders found!"));
        }
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", orders));
    }

    @GetMapping("/shop/order/details/{id}")
    public ResponseEntity<?> getOrderDetails(@PathVariable String id) {
        try {
            Order order = orderService.getOrderDetails(id);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", order
            ));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", false,
                    "message", "Order not found!"
            ));
        }
    }






    // GET all orders
    @GetMapping("/admin/orders/get")
    public ResponseEntity<?> getAllOrdersAdmin() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", false,
                    "message", "No orders found!"
            ));
        }
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", orders
        ));
    }

    // GET a single order by ID
    @GetMapping("/admin/orders/details/{id}")
    public ResponseEntity<?> getOrderDetailsAdmin(@PathVariable String id) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", false,
                    "message", "Order not found!"
            ));
        }
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", order.get()
        ));
    }

    // PUT update order status
    @PutMapping("/admin/orders/update/{id}")
    public ResponseEntity<?> updateOrderStatusAdmin(@PathVariable String id, @RequestBody Map<String, String> body) {
        String orderStatus = body.get("orderStatus");
        boolean updated = orderService.updateOrderStatus(id, orderStatus);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "success", false,
                    "message", "Order not found!"
            ));
        }
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Order status is updated successfully!"
        ));
    }
}
