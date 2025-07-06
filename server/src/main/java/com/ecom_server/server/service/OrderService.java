package com.ecom_server.server.service;

import com.ecom_server.server.model.Order;
import com.ecom_server.server.model.Product;
import com.ecom_server.server.model.submodel.CaptureRequest;
import com.ecom_server.server.model.submodel.CartItem_orders;
import com.ecom_server.server.model.submodel.OrderRequest;
import com.ecom_server.server.repository.CartRepository;
import com.ecom_server.server.repository.OrderRepository;
import com.ecom_server.server.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final PaypalService paypalService;

    public Map<String, Object> createOrder(OrderRequest request) throws Exception {
        String approvalURL = paypalService.createPaypalPayment(request.getCartItems(), request.getTotalAmount());

        Order order = new Order();
        BeanUtils.copyProperties(request, order);
        order = orderRepository.save(order);

        return Map.of("approvalURL", approvalURL, "orderId", order.getId());
    }

    public Order capturePayment(CaptureRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new NoSuchElementException("Order not found"));

        order.setPaymentStatus("paid");
        order.setOrderStatus("confirmed");
        order.setPaymentId(request.getPaymentId());
        order.setPayerId(request.getPayerId());

        for (CartItem_orders item : order.getCartItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new NoSuchElementException("Product not found"));
            if (product.getTotalStock() < item.getQuantity()) {
                throw new NoSuchElementException("Not enough stock for this product: " + product.getTitle());
            }
            product.setTotalStock(product.getTotalStock() - item.getQuantity());
            productRepository.save(product);
        }

        cartRepository.deleteById(order.getCartId());
        return orderRepository.save(order);
    }

    public List<Order> getAllOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderDetails(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
    }



    // Get all orders for admin
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get a specific order by ID
    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    // Update order status
    public boolean updateOrderStatus(String id, String orderStatus) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

}
